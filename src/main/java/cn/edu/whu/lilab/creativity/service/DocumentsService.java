package cn.edu.whu.lilab.creativity.service;

import cn.edu.whu.lilab.creativity.domain.Documents;
import cn.edu.whu.lilab.creativity.dto.DocumentInfoDto;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DocumentsService extends IService<Documents> {

    /**
     * 基于指定外部id获取论文详情
     *
     * @param externalId
     * @return
     */
    DocumentInfoDto findInfoById(String externalId);

    /*
     *根据外部id获取数据库自增id
     */
    Integer findDocumentIdByExternalId(String externalId);

}











