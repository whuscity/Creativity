package cn.edu.whu.lilab.creativity.service;

import cn.edu.whu.lilab.creativity.domain.DocumentsCite;
import cn.edu.whu.lilab.creativity.dto.CiteRelationPaperDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DocumentsCiteService extends IService<DocumentsCite> {


    /**
     * 分页获取指定PMID的参考文献列表，按指定类型排序
     *
     * @param pmid
     * @param page
     * @param orderType
     * @return
     */
    Page<CiteRelationPaperDto> findRefById(String pmid, Page<CiteRelationPaperDto> page, String orderType);

    /**
     * 分页获取指定PMID的引证文献列表，按指定类型排序
     *
     * @param pmid
     * @param page
     * @param orderType
     * @return
     */
    Page<CiteRelationPaperDto> findCitingById(String pmid, Page<CiteRelationPaperDto> page, String orderType);
}




