package cn.edu.whu.lilab.creativity.service;

import cn.edu.whu.lilab.creativity.domain.DocumentAbstract;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DocumentAbstractService extends IService<DocumentAbstract> {

    /**
     * 基于外部ID获取摘要对象
     * @param externalId
     * @return
     */
    DocumentAbstract findByExternalId(String externalId);

}






