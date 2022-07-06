package cn.edu.whu.lilab.creativity.mapper;

import cn.edu.whu.lilab.creativity.domain.DocumentCite;
import cn.edu.whu.lilab.creativity.dto.CiteRelationPaperDto;import com.baomidou.mybatisplus.core.mapper.BaseMapper;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;import org.apache.ibatis.annotations.Param;

public interface DocumentCiteMapper extends BaseMapper<DocumentCite> {
    Page<CiteRelationPaperDto> getRefListById(Page<CiteRelationPaperDto> page, @Param("pmid") String pmid);

    Page<CiteRelationPaperDto> getCitingListById(Page<CiteRelationPaperDto> page, @Param("pmid") String pmid);
}