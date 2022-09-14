package cn.edu.whu.lilab.creativity.service.impl;

import cn.edu.whu.lilab.creativity.converter.DocumentsConverter;
import cn.edu.whu.lilab.creativity.dto.DocumentInfoDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.whu.lilab.creativity.mapper.DocumentsMapper;
import cn.edu.whu.lilab.creativity.domain.Documents;
import cn.edu.whu.lilab.creativity.service.DocumentsService;

@Service
public class DocumentsServiceImpl extends ServiceImpl<DocumentsMapper, Documents> implements DocumentsService {

    @Autowired
    private DocumentsMapper documentsMapper;

    @Autowired
    private DocumentsConverter documentsConverter;


    @Override
    public DocumentInfoDto findInfoById(String pmid) {
        Documents documents = documentsMapper.selectOne(new LambdaQueryWrapper<Documents>()
                .eq(Documents::getExternalId, pmid));

        return documentsConverter.converDocumentInfoDto(documents);

    }

    @Override
    public Integer findDocumentIdByExternalId(String externalId) {
        Documents documents = documentsMapper.selectOne(new LambdaQueryWrapper<Documents>()
                .eq(Documents::getExternalId, externalId));
        return documents.getDocumentId();
    }
}







