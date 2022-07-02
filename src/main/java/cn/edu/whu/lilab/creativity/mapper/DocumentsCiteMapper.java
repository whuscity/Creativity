package cn.edu.whu.lilab.creativity.mapper;

import cn.edu.whu.lilab.creativity.domain.DocumentsCite;
import cn.edu.whu.lilab.creativity.dto.CiteRelationPaperDto;import com.baomidou.mybatisplus.core.mapper.BaseMapper;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;import org.apache.ibatis.annotations.Param;

public interface DocumentsCiteMapper extends BaseMapper<DocumentsCite> {
    /**
     * 分页获取指定PMID的参考文献列表
     *
     * @param page
     * @param pmid
     * @return
     */
    Page<CiteRelationPaperDto> getRefListById(Page<CiteRelationPaperDto> page, @Param("pmid") String pmid);

    /**
     * 分页获取指定PMID的引证考文献列表
     *
     * @param page
     * @param pmid
     * @return
     */
    Page<CiteRelationPaperDto> getCitingListById(Page<CiteRelationPaperDto> page, @Param("pmid") String pmid);
}