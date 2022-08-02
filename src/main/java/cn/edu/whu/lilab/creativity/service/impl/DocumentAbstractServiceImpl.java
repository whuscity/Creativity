package cn.edu.whu.lilab.creativity.service.impl;

import cn.edu.whu.lilab.creativity.domain.DocumentAbstract;
import cn.edu.whu.lilab.creativity.mapper.DocumentAbstractMapper;
import cn.edu.whu.lilab.creativity.service.DocumentAbstractService;
import cn.edu.whu.lilab.creativity.service.DocumentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentAbstractServiceImpl extends ServiceImpl<DocumentAbstractMapper, DocumentAbstract> implements DocumentAbstractService {

    @Autowired
    private DocumentsService documentsService;

    @Override
    public DocumentAbstract findByExternalId(String externalId) {
        Integer documentId = documentsService.findDocumentIdByExternalId(externalId);
        DocumentAbstract abstractInfo = getById(documentId);
        return abstractInfo;
    }
}








