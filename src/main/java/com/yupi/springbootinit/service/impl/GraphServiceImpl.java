package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.mapper.GraphMapper;
import com.yupi.springbootinit.model.entity.Graph;
import com.yupi.springbootinit.service.GraphService;
import org.springframework.stereotype.Service;

/**
* @author 29452
* @description 针对表【graph(图表信息)】的数据库操作Service实现
* @createDate 2024-04-24 12:59:58
*/
@Service
public class GraphServiceImpl extends ServiceImpl<GraphMapper, Graph>
    implements GraphService{

}




