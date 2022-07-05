package cn.edu.whu.lilab.creativity.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.whu.lilab.creativity.domain.DocumentAuthors;
import cn.edu.whu.lilab.creativity.mapper.DocumentAuthorsMapper;
import cn.edu.whu.lilab.creativity.service.DocumentAuthorsService;

@Service
public class DocumentAuthorsServiceImpl extends ServiceImpl<DocumentAuthorsMapper, DocumentAuthors> implements DocumentAuthorsService {

}

