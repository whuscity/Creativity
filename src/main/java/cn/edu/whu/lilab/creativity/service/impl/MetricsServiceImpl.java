package cn.edu.whu.lilab.creativity.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.whu.lilab.creativity.domain.Metrics;
import cn.edu.whu.lilab.creativity.mapper.MetricsMapper;
import cn.edu.whu.lilab.creativity.service.MetricsService;
@Service
public class MetricsServiceImpl extends ServiceImpl<MetricsMapper, Metrics> implements MetricsService{

}
