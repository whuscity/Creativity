package cn.edu.whu.lilab.creativity.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.whu.lilab.creativity.mapper.DocumentsMapper;
import cn.edu.whu.lilab.creativity.domain.Documents;
import cn.edu.whu.lilab.creativity.service.DocumentsService;

@Service
public class DocumentsServiceImpl extends ServiceImpl<DocumentsMapper, Documents> implements DocumentsService {

}


