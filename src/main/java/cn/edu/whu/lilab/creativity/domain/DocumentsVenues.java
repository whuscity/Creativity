package cn.edu.whu.lilab.creativity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "documents_venues")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "documents_venues")
public class DocumentsVenues {
    @TableId(value = "document_id", type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private Integer documentId;

    /**
     * 出版物id
     */
    @TableField(value = "venue_id")
    @ApiModelProperty(value = "出版物id")
    private Integer venueId;

    /**
     * 出版物卷
     */
    @TableField(value = "venue_volume")
    @ApiModelProperty(value = "出版物卷")
    private String venueVolume;

    /**
     * 出版物期
     */
    @TableField(value = "venue_issue")
    @ApiModelProperty(value = "出版物期")
    private String venueIssue;

    /**
     * 出版物标题
     */
    @TableField(value = "venue_title")
    @ApiModelProperty(value = "出版物标题")
    private String venueTitle;

    @TableField(value = "venue_year")
    @ApiModelProperty(value = "")
    private String venueYear;

    @TableField(value = "venue_page")
    @ApiModelProperty(value = "")
    private String venuePage;

    @TableField(value = "venue_str")
    @ApiModelProperty(value = "")
    private String venueStr;

    /**
     * // int mapping 明确
     */
    @TableField(value = "venue_type")
    @ApiModelProperty(value = "// int mapping 明确")
    private Integer venueType;
}