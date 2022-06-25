package cn.edu.whu.lilab.creativity.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.whu.lilab.creativity.mapper.DocumentsCiteMapper;
import cn.edu.whu.lilab.creativity.domain.DocumentsCite;
import cn.edu.whu.lilab.creativity.service.DocumentsCiteService;
@Service
public class DocumentsCiteServiceImpl extends ServiceImpl<DocumentsCiteMapper, DocumentsCite> implements DocumentsCiteService{

}
