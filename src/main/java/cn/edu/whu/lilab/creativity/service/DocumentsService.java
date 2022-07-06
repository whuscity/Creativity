package cn.edu.whu.lilab.creativity.service;

import cn.edu.whu.lilab.creativity.domain.Documents;
import cn.edu.whu.lilab.creativity.dto.DocumentInfoDto;
import cn.edu.whu.lilab.creativity.dto.SearchResultDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DocumentsService extends IService<Documents> {

    /**
     * 基于指定外部id获取论文详情
     *
     * @param externalId
     * @return
     */
    DocumentInfoDto findInfoById(String externalId);

    /**
     * 基于doi获取唯一搜索结果
     * @param doi
     * @return
     */
    SearchResultDto findSearchResultByDoi(String doi);

    /**
     * 基于pmid获取唯一搜索结果
     * @param pmid
     * @return
     */
    SearchResultDto findSearchResultByPmid(String pmid);

    /**
     * 基于标题搜索结果，并按指定类型排序
     * @param titleQuery
     * @param page
     * @param orderType
     * @return
     */
    Page<SearchResultDto> findSearchResultByTitle(String titleQuery, Page<SearchResultDto> page, String orderType);
}







