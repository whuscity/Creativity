package cn.edu.whu.lilab.creativity.converter;

import cn.edu.whu.lilab.creativity.domain.Documents;
import cn.edu.whu.lilab.creativity.dto.DocumentInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentConverter {


    @Mapping(source = "externalId",target = "pmid")
    DocumentInfoDto converDocumentInfoDto(Documents documents);
}
