package cn.edu.whu.lilab.creativity.service.impl;

import cn.edu.whu.lilab.creativity.converter.DocumentConverter;
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
    private DocumentConverter documentConverter;

    @Override
    public DocumentInfoDto findInfoById(String pmid) {
        Documents documents = documentsMapper.selectOne(new LambdaQueryWrapper<Documents>()
                .eq(Documents::getExternalId, pmid));

        return documentConverter.converDocumentInfoDto(documents);

    }
}



