package cn.edu.whu.lilab.creativity.service;

import cn.edu.whu.lilab.creativity.domain.DocumentCite;
import cn.edu.whu.lilab.creativity.dto.CiteRelationPaperDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DocumentCiteService extends IService<DocumentCite> {


    Page<CiteRelationPaperDto> findRefById(String pmid, Page<CiteRelationPaperDto> page, String orderType);

    Page<CiteRelationPaperDto> findCitingById(String pmid, Page<CiteRelationPaperDto> page, String orderType);
}



