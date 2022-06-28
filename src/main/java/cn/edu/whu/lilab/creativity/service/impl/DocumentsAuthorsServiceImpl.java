package cn.edu.whu.lilab.creativity.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.whu.lilab.creativity.mapper.DocumentsAuthorsMapper;
import cn.edu.whu.lilab.creativity.domain.DocumentsAuthors;
import cn.edu.whu.lilab.creativity.service.DocumentsAuthorsService;

@Service
public class DocumentsAuthorsServiceImpl extends ServiceImpl<DocumentsAuthorsMapper, DocumentsAuthors> implements DocumentsAuthorsService {

}

