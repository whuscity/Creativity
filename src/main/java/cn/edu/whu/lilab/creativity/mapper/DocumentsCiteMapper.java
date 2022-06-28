package cn.edu.whu.lilab.creativity.mapper;

import cn.edu.whu.lilab.creativity.Vo.CiteRelationPaperVo;
import cn.edu.whu.lilab.creativity.domain.DocumentsCite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface DocumentsCiteMapper extends BaseMapper<DocumentsCite> {

    /**
     * 分页获取指定PMID的参考文献列表
     * @param page
     * @param pmid
     * @return
     */
    Page<CiteRelationPaperVo>  getRefListById(Page<CiteRelationPaperVo> page,@Param("pmid") String pmid);


}