package cn.edu.whu.lilab.creativity.service;

import cn.edu.whu.lilab.creativity.domain.DocumentsCite;
import cn.edu.whu.lilab.creativity.Vo.CiteRelationPaperVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DocumentsCiteService extends IService<DocumentsCite> {


    /**
     * 分页获取指定PMID的参考文献列表，按指定类型排序
     * @param pmid
     * @param page
     * @param orderType
     * @return
     */
    Page<CiteRelationPaperVo> findById(String pmid, Page<CiteRelationPaperVo> page, String orderType);
}



