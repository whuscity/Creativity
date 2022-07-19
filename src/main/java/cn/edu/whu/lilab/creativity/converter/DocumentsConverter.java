package cn.edu.whu.lilab.creativity.converter;

import cn.edu.whu.lilab.creativity.domain.Documents;
import cn.edu.whu.lilab.creativity.dto.DocumentInfoDto;
import cn.edu.whu.lilab.creativity.dto.SearchResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentsConverter {


    // @Mapping(source = "publishDate",target = "publishDate",dateFormat = "yyyy-MM-dd")
    DocumentInfoDto converDocumentInfoDto(Documents documents);

    SearchResultDto convertSearchResultDto(Documents documents);
}
