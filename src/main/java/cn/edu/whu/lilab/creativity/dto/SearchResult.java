package cn.edu.whu.lilab.creativity.dto;

import cn.edu.whu.lilab.creativity.dto.elasticsearch.AggRecords;
import cn.edu.whu.lilab.creativity.dto.elasticsearch.SearchResultRecords;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询结果完整信息")
public class SearchResult {

    @ApiModelProperty(value = "击中记录总数")
    Long hitTotal;

    @ApiModelProperty(value = "当前页")
    Integer current;

    @ApiModelProperty(value = "每页显示条数")
    Integer size;

    @ApiModelProperty(value = "起始出版年")
    Integer startPublishYear;

    @ApiModelProperty(value = "终止出版年")
    Integer endPublishYear;

    @ApiModelProperty(value = "查询结果记录")
    List<SearchResultRecords> searchResultRecords;

    @ApiModelProperty(value = "文档类型记录")
    List<AggRecords> documentTypeRecords;

    @ApiModelProperty(value = "出版物名记录")
    List<AggRecords> venueNameRecords;

    @ApiModelProperty(value = "所属子领域记录")
    List<AggRecords> subfieldRecords;


    public SearchResult(SearchQuery searchQuery) {
        this.current = searchQuery.getCurrent();
        this.size = searchQuery.getSize();
        this.startPublishYear = searchQuery.getStartPublishYear();
        this.endPublishYear = searchQuery.getEndPublishYear();
    }


}
