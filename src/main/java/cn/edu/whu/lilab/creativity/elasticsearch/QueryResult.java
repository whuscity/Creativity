package cn.edu.whu.lilab.creativity.elasticsearch;

import cn.edu.whu.lilab.creativity.constants.EsConstants;
import cn.edu.whu.lilab.creativity.dto.SearchQuery;
import cn.edu.whu.lilab.creativity.dto.SearchResult;
import cn.edu.whu.lilab.creativity.dto.elasticsearch.AggRecords;
import cn.edu.whu.lilab.creativity.dto.elasticsearch.SearchResultRecords;
import cn.edu.whu.lilab.creativity.enums.OrderType;
import cn.edu.whu.lilab.creativity.enums.SearchType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
@Slf4j
public class QueryResult {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 展示索引及映射  实际利用kibana构建
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
                "        \"analyzer\": \"english\",\n" +
                "        \"fields\": {\n" +
                "          \"keyword\": {\n" +
                "            \"type\": \"keyword\"\n" +
                "          }\n" +
                "        }\n" +
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
                "       \"abstract_full\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"english\"\n" +
                "      },\n" +
                "      \"keywords_str\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"english\"\n" +
                "      },\n" +
                "      \"cite_count\": {\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"publish_year\": {\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"document_id\": {\n" +
                "        \"type\": \"integer\"\n" +
                "      },\n" +
                "      \"doi\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"venue_name\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"english\",\n" +
                "        \"fields\": {\n" +
                "          \"keyword\": {\n" +
                "            \"type\": \"keyword\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"subfield\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      }\n" +
                "    }\n" +
                "  }", XContentType.JSON);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        log.info("是否创建索引成功：{}", createIndexResponse.isAcknowledged());
        restHighLevelClient.close(); //关闭资源
    }

    /**
     * 增加过滤查询条件并判断查询类型
     *
     * @param searchQuery
     * @return
     */
    public SearchResult search(SearchQuery searchQuery) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().trackTotalHits(true);

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //1.增加过滤条件
        addFilter(searchQuery, boolQueryBuilder);
        //2.增加查询条件
        addSearch(searchQuery, boolQueryBuilder);
        sourceBuilder.query(boolQueryBuilder);


        //3.增加聚合条件 桶聚合
        sourceBuilder.aggregation(AggregationBuilders.terms("documentType_agg").field("document_type").size(EsConstants.MAX_AGG_COUNT));
        sourceBuilder.aggregation(AggregationBuilders.terms("venueName_agg").field("venue_name.keyword").size(EsConstants.MAX_AGG_COUNT));
        sourceBuilder.aggregation(AggregationBuilders.terms("subfield_agg").field("subfield").size(EsConstants.MAX_AGG_COUNT));

        //4.返回指定字段
        String[] includeFileds = new String[]{"document_id", "external_id", "doi", "title", "document_type", "authors_name_str", "venue_str", "abstract_short", "keywords_str", "cite_count", "publish_year", "venue_name", "subfield"};
        String[] excluedFileds = new String[]{};
        sourceBuilder.fetchSource(includeFileds, excluedFileds);
        SearchResult searchResult = new SearchResult(searchQuery);

        getSearchResultDtos(searchQuery, searchResult, sourceBuilder);
        return searchResult;

    }

    /**
     * 增加过滤条件
     *
     * @param searchQuery
     * @param boolQueryBuilder
     */
    private void addFilter(SearchQuery searchQuery, BoolQueryBuilder boolQueryBuilder) {
        //1.基于出版年过滤
        if (searchQuery.getStartPublishYear() != null && searchQuery.getStartPublishYear() != null) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("publish_year")
                    .gte(searchQuery.getStartPublishYear())
                    .lte(searchQuery.getEndPublishYear()));
        }
        //2.基于文档类型过滤
        if (searchQuery.getDocumentType() != null && searchQuery.getDocumentType().size() != 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("document_type", searchQuery.getDocumentType()));
        }
        //3.基于出版物名过滤
        if (searchQuery.getVenueName() != null && searchQuery.getVenueName().size() != 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("venue_name.keyword", searchQuery.getVenueName()));
        }
        //4.基于研究领域过滤
        if (searchQuery.getSubfield() != null && searchQuery.getSubfield().size() != 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("subfield", searchQuery.getSubfield()));
        }
    }

    /**
     * 增加查询条件
     *
     * @param searchQuery
     * @param boolQueryBuilder
     */
    private void addSearch(SearchQuery searchQuery, BoolQueryBuilder boolQueryBuilder) {
        // 判断查询类型：title、doi、pmid
        //doi: 10.+ 四位数字 + / + 无限制后缀
        Pattern doiPattern = Pattern.compile("^10\\.\\d{4}/.+$");
        //pmid: 全数字 最大八位
        Pattern pmidPattern = Pattern.compile("^\\d{1,8}$");

        //防止抛异常
        if (StringUtils.isEmpty(searchQuery.getQuery())) {
            searchQuery.setQuery("");
        }

        //2.增加查询条件
        if (doiPattern.matcher(searchQuery.getQuery()).matches()) { //doi查询
            searchByDoi(searchQuery, boolQueryBuilder);
        } else if (pmidPattern.matcher(searchQuery.getQuery()).matches()) { //pmid查询
            searchByPmid(searchQuery, boolQueryBuilder);
        } else { //title查询
            searchByTitle(searchQuery, boolQueryBuilder);
        }
    }


    /**
     * 基于标题查询
     *
     * @param searchQuery
     * @param boolQueryBuilder
     * @return
     */
    private void searchByTitle(SearchQuery searchQuery, BoolQueryBuilder boolQueryBuilder) {

        //如果查询条件为空
        if (StringUtils.isEmpty(searchQuery.getQuery()) || searchQuery.getQuery().equals("")) {
            boolQueryBuilder.must(QueryBuilders.matchAllQuery());
            if (StringUtils.isEmpty(searchQuery.getOrderType()) || searchQuery.getOrderType().equals("")) {
                searchQuery.setOrderType(OrderType.PUBLICATION_YEAR.getCode());
            }

        } else {
            boolQueryBuilder.should(QueryBuilders.termQuery("title.keyword", searchQuery.getQuery())).boost(20);
            boolQueryBuilder.should(QueryBuilders.termQuery("venue_name.keyword", searchQuery.getQuery())).boost(10); //TODO 提升权重有意义吗
            boolQueryBuilder.must(QueryBuilders.combinedFieldsQuery(searchQuery.getQuery(), "title", "abstract_full", "keywords_str","venue_name").field("title", 2).field("keywords_str", 3)
                    .minimumShouldMatch("2<70%")
                    .operator(Operator.OR));

        }

    }

    /**
     * 基于doi查询
     *
     * @param searchQuery
     * @param boolQueryBuilder
     * @return
     */
    private void searchByDoi(SearchQuery searchQuery, BoolQueryBuilder boolQueryBuilder) {
        boolQueryBuilder.must(QueryBuilders.termQuery(SearchType.DOI.getCode(), searchQuery.getQuery()));

    }

    /**
     * 基于pmid查询
     *
     * @param searchQuery
     * @param boolQueryBuilder
     * @return
     */
    private void searchByPmid(SearchQuery searchQuery, BoolQueryBuilder boolQueryBuilder) {
        boolQueryBuilder.must(QueryBuilders.termQuery(SearchType.PMID.getCode(), searchQuery.getQuery()));
    }


    private void getSearchResultDtos(SearchQuery searchQuery, SearchResult searchResult, SearchSourceBuilder sourceBuilder) {
        if (StringUtils.isEmpty(searchQuery.getOrderType()) || searchQuery.getOrderType().equals("")) {
            searchQuery.setOrderType(OrderType.RELEVANCE.getCode());
        }
        //1.增加排序依据
        switch (searchQuery.getOrderType()) {
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

        // 2.增加分页查询
        sourceBuilder.from((int) ((searchQuery.getCurrent() - 1) * searchQuery.getSize())) //起始位置
                .size((int) searchQuery.getSize());

        SearchRequest searchRequest = new SearchRequest("documents_info");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (!searchResponse.status().equals(RestStatus.OK)) {
            return;
        }
        // 1.设置击中记录总数
        long hitTotal = searchResponse.getHits().getTotalHits().value;
        searchResult.setHitTotal(hitTotal);
        log.info("击中记录数{}", hitTotal);
        if (hitTotal == 0) {
            return;
        }

        // 2.设置查询结果记录
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<SearchResultRecords> searchResultRecordsList = new ArrayList<>();
        for (SearchHit hit : hits) {
            SearchResultRecords searchResultRecords = null;
            try {
                searchResultRecords = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                        .readValue(hit.getSourceAsString(), SearchResultRecords.class);
                searchResultRecordsList.add(searchResultRecords);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                continue;
            }

        }
        searchResult.setSearchResultRecords(searchResultRecordsList);

        //获取聚合结果
        // 3.设置文档类型记录
        List<AggRecords> documentTypeRecordsList = new ArrayList<>();
        Terms documentTypeAggResultList = searchResponse.getAggregations().get("documentType_agg");
        for (Terms.Bucket buck : documentTypeAggResultList.getBuckets()) {
            AggRecords documentTypeRecords = new AggRecords(buck.getKeyAsString(), buck.getDocCount());
            documentTypeRecordsList.add(documentTypeRecords);
        }
        searchResult.setDocumentTypeRecords(documentTypeRecordsList);

        // 4.设置出版物名记录
        List<AggRecords> venueNameRecordsList = new ArrayList<>();
        Terms venueNameAggResultList = searchResponse.getAggregations().get("venueName_agg");
        for (Terms.Bucket buck : venueNameAggResultList.getBuckets()) {
            AggRecords venueNameRecords = new AggRecords(buck.getKeyAsString(), buck.getDocCount());
            venueNameRecordsList.add(venueNameRecords);
        }
        searchResult.setVenueNameRecords(venueNameRecordsList);

        // 5.设置所属子领域记录
        List<AggRecords> subfieldRecordsList = new ArrayList<>();
        Terms subfieldAggResultList = searchResponse.getAggregations().get("subfield_agg");
        for (Terms.Bucket buck : subfieldAggResultList.getBuckets()) {
            AggRecords subfieldRecords = new AggRecords(buck.getKeyAsString(), buck.getDocCount());
            subfieldRecordsList.add(subfieldRecords);
        }
        searchResult.setSubfieldRecords(subfieldRecordsList);
    }


    /**
     * 基于设置的最大查询数上限，注意此处无效
     *
     * @param page
     * @param total
     */
    // private void setMaxPages(Page<SearchResultDto> page,  int total) {
    //     if (page.getSize() != 0 && total > EsConstants.MAX_RESULT_COUNT) {
    //         long pages = EsConstants.MAX_RESULT_COUNT / page.getSize();
    //         if (EsConstants.MAX_RESULT_COUNT % page.getSize() != 0) {
    //             pages++;
    //         }
    //         page.setPages(pages);
    //     }
    // }


}
