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

@ApiModel(value = "document_venues")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "document_venues")
public class DocumentVenues {
    @TableField(value = "document_id")
    @ApiModelProperty(value = "")
    private Integer documentId;

    @TableField(value = "venue_id")
    @ApiModelProperty(value = "")
    private Integer venueId;

    /**
     * 出版物展示名
     */
    @TableField(value = "venue_display_name")
    @ApiModelProperty(value = "出版物展示名")
    private String venueDisplayName;

    /**
     * 出版物名
     */
    @TableField(value = "venue_name")
    @ApiModelProperty(value = "出版物名")
    private String venueName;

    /**
     * 出版物年
     */
    @TableField(value = "venue_year")
    @ApiModelProperty(value = "出版物年")
    private String venueYear;

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
     * 出版物页
     */
    @TableField(value = "venue_page")
    @ApiModelProperty(value = "出版物页")
    private String venuePage;

    /**
     * 出版物类型
     */
    @TableField(value = "venue_type")
    @ApiModelProperty(value = "出版物类型")
    private String venueType;
}