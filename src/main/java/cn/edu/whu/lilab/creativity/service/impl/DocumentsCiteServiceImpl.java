package cn.edu.whu.lilab.creativity.service.impl;

import cn.edu.whu.lilab.creativity.Vo.CiteRelationPaperVo;
import cn.edu.whu.lilab.creativity.enums.OrderType;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.whu.lilab.creativity.mapper.DocumentsCiteMapper;
import cn.edu.whu.lilab.creativity.domain.DocumentsCite;
import cn.edu.whu.lilab.creativity.service.DocumentsCiteService;

@Service
public class DocumentsCiteServiceImpl extends ServiceImpl<DocumentsCiteMapper, DocumentsCite> implements DocumentsCiteService {

    @Autowired
    private DocumentsCiteMapper documentsCiteMapper;

    /**
     * 分页获取指定PMID的参考文献列表，按指定类型排序
     * @param pmid
     * @param page
     * @param orderType
     * @return
     */
    @Override
    public Page<CiteRelationPaperVo> findById(String pmid, Page<CiteRelationPaperVo> page, String orderType) {

        switch (orderType) {
            case "year":
                page.addOrder(OrderItem.desc(OrderType.CITED_YEAR.getValue()));
                break;
            case "cite_count":
                page.addOrder(OrderItem.desc(OrderType.CITE_COUNT.getValue()));
                break;
            default: //默认创新性指数排序
                page.addOrder(OrderItem.desc(OrderType.CREATIVITY_INDEX.getValue()));
        }

        return documentsCiteMapper.getRefListById(page,pmid);
    }


}



