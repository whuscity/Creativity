package cn.edu.whu.lilab.creativity.service;

import cn.edu.whu.lilab.creativity.domain.Documents;
import cn.edu.whu.lilab.creativity.dto.DocumentInfoDto;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DocumentsService extends IService<Documents> {

    /**
     * 基于指定pmid获取论文详情
     * @param pmid
     * @return
     */
    DocumentInfoDto findInfoById(String pmid);
}



