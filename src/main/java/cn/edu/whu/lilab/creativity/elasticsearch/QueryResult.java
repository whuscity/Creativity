package cn.edu.whu.lilab.creativity.elasticsearch;

import cn.edu.whu.lilab.creativity.constants.EsConstants;
import cn.edu.whu.lilab.creativity.dto.SearchResultDto;
import cn.edu.whu.lilab.creativity.enums.OrderType;
import cn.edu.whu.lilab.creativity.enums.SearchType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class QueryResult {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 展示索引及映射  已利用kibana构建索引及映射
     *
     * @throws IOException
     */
    private void createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("documents_info");
        createIndexRequest.mapping("{\n" +
                "    \"properties\": {\n" +
                "      \"external_id\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"title\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"english\", \n" +
                "        \"fields\": {\n" +
                "          \"keyword\":{\n" +
                "            \"type\":\"keyword\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"boost\": 2\n" +
                "      },\n" +
                "      \"authors_name_str\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"english\"\n" +
                "      },\n" +
                "      \"document_type\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"venue_str\": {\n" +
                "        \"type\": \"text\"\n" +
                "      },\n" +
                "      \"abstract_short\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"english\"\n" +
                "      },\n" +
                "      \"keywords_str\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"english\",\n" +
                "        \"boost\": 3\n" +
                "      },\n" +
                "      \"cite_count\": {\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"publish_year\": {\n" +
                "        \"type\": \"integer\"\n" +
                "      \n" +
                "      },\n" +
                "      \"document_id\":{\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"doi\":{\n" +
                "        \"type\":\"keyword\"\n" +
                "      }\n" +
                "    }\n" +
                "  }", XContentType.JSON);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        log.info("是否创建索引成功：{}", createIndexResponse.isAcknowledged());
        restHighLevelClient.close(); //关闭资源
    }

    /**
     * 基于标题查询
     *
     * @param titleQuery
     * @param page
     * @param orderType
     * @return
     * @throws IOException
     */
    public void searchByTitle(String titleQuery, Page<SearchResultDto> page, String orderType) {


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().trackTotalHits(true);

        //如果查询条件为空
        if(StringUtils.isEmpty(titleQuery) || titleQuery.equals("")){
            sourceBuilder.query(QueryBuilders.matchAllQuery())
                    .from((int) ((page.getCurrent() - 1) * page.getSize())) //起始位置
                    .size((int) page.getSize());
            if(StringUtils.isEmpty(orderType) || orderType.equals("")){
                orderType = OrderType.PUBLICATION_YEAR.getCode();
            }
             getSearchResultDtos(sourceBuilder,page, orderType);
        }
        //分页查询
        sourceBuilder.query(QueryBuilders.matchQuery(SearchType.TITLE.getCode(),titleQuery))

        // sourceBuilder.query(QueryBuilders.multiMatchQuery(titleQuery,SearchType.TITLE.getCode(),SearchType.TITLE.getCode()+".keyword"))

                .from((int) ((page.getCurrent() - 1) * page.getSize())) //起始位置
                .size((int) page.getSize());

         getSearchResultDtos(sourceBuilder, page,orderType);

    }

    /**
     * 基于doi查询
     *
     * @param doi
     * @return
     */
    public void searchByDoi(String doi, Page<SearchResultDto> page, String orderType) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery(SearchType.DOI.getCode(), doi));
         getSearchResultDtos(sourceBuilder, page,orderType);
    }

    /**
     * 基于pmid查询
     *
     * @param pmid
     * @return
     */
    public void searchByPmid(String pmid, Page<SearchResultDto> page, String orderType) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery(SearchType.PMID.getCode(), pmid));
         getSearchResultDtos(sourceBuilder, page,orderType);
    }

    private void getSearchResultDtos(SearchSourceBuilder sourceBuilder, Page<SearchResultDto> page, String orderType) {
        if(StringUtils.isEmpty(orderType) || orderType.equals("")){
            orderType = OrderType.RELEVANCE.getCode();
        }
        //排序依据
        switch (orderType) {
            case "publish_year":
                sourceBuilder.sort(OrderType.PUBLICATION_YEAR.getCode(), SortOrder.DESC)
                        .sort(OrderType.RELEVANCE.getCode(), SortOrder.DESC);
                break;
            case "cite_count":
                sourceBuilder.sort(OrderType.CITE_COUNT.getCode(), SortOrder.DESC)
                        .sort(OrderType.RELEVANCE.getCode(), SortOrder.DESC);
                break;
            default: //默认相关度排序
                sourceBuilder.sort(OrderType.RELEVANCE.getCode(), SortOrder.DESC)
                        .sort(OrderType.PUBLICATION_YEAR.getCode(), SortOrder.DESC);

        }

        SearchRequest searchRequest = new SearchRequest("documents_info");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        //击中记录总数
        long hitTotal = searchResponse.getHits().getTotalHits().value;
        log.info("击中记录数{}",hitTotal);
        if (hitTotal == 0) {
            return ;
        }

        SearchHit[] hits = searchResponse.getHits().getHits();
        List<SearchResultDto> searchResultDtos = new ArrayList<>();
        for (SearchHit hit : hits) {
            SearchResultDto searchResultDto = null;
            try {
                searchResultDto = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                        .readValue(hit.getSourceAsString(), SearchResultDto.class);
                searchResultDtos.add(searchResultDto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                continue;
            }

        }

        setMaxPages(page, (int) hitTotal);
         page.setRecords(searchResultDtos).setTotal(hitTotal);

    }

    /**
     * 基于设置的最大查询数上限，计算最大页数
     *
     * @param page
     * @param total
     */
    private void setMaxPages(Page<SearchResultDto> page,  int total) {
        if (page.getSize() != 0 && total > EsConstants.MAX_RESULT_COUNT) {
            long pages = EsConstants.MAX_RESULT_COUNT / page.getSize();
            if (EsConstants.MAX_RESULT_COUNT % page.getSize() != 0) {
                pages++;
            }
            page.setPages(pages);
        }
    }

}
