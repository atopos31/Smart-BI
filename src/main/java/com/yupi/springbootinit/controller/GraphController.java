package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.manager.AiManager;
import com.yupi.springbootinit.model.dto.graph.GenGraphByAiRequest;
import com.yupi.springbootinit.model.dto.graph.GraphAddRequest;
import com.yupi.springbootinit.model.dto.graph.GraphQueryRequest;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.model.entity.Graph;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.vo.AIGraphVO;
import com.yupi.springbootinit.service.GraphService;
import com.yupi.springbootinit.service.UserService;
import com.yupi.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/graph")
@Slf4j
public class GraphController {
    @Resource
    private AiManager aiManager;

    @Resource
    private UserService userService;

    @Resource
    private GraphService graphService;

    public static Long GraphModelId = 1782597004447621122L;

    /**
     * 创建
     *
     * @param graphAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addGraph(@RequestBody GraphAddRequest graphAddRequest,HttpServletRequest request) {
        if (graphAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);

        Graph graph = new Graph();
        graph.setName(graphAddRequest.getName());
        graph.setUserid(loginUser.getId());
        graph.setGoal(graphAddRequest.getGoal());
        graph.setGengraph(graphAddRequest.getGengraph());
        graph.setGenresult(graphAddRequest.getGenresult());
        boolean result = graphService.save(graph);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(graph.getId());
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Graph> getGraphVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Graph graph = graphService.getById(id);
        if (graph == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(graph);
    }

    @PostMapping("/my/list/page")
    public BaseResponse<Page<Graph>> listMyGraphVOByPage(@RequestBody GraphQueryRequest graphQueryRequest,
                                                         HttpServletRequest request) {
        if (graphQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        graphQueryRequest.setUserId(loginUser.getId());
        long current = graphQueryRequest.getCurrent();
        long size = graphQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Graph> graphPage = graphService.page(new Page<>(current, size),getQueryWrapper(graphQueryRequest));
        return ResultUtils.success(graphPage);
    }
    /**
     * 获取查询包装类
     *
     * @param graphQueryRequest
     * @return
     */
    private QueryWrapper<Graph> getQueryWrapper(GraphQueryRequest graphQueryRequest) {
        QueryWrapper<Graph> queryWrapper = new QueryWrapper<>();
        if (graphQueryRequest == null) {
            return queryWrapper;
        }
        Long userId = graphQueryRequest.getUserId();
        String sortField = graphQueryRequest.getSortField();
        String sortOrder = graphQueryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId),"userId",userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 智能绘制
     *
     * @param genGraphByAiRequest
     * @return
     */
    @PostMapping("/gen")
    public BaseResponse<Graph> genGraphByAi(@RequestBody GenGraphByAiRequest genGraphByAiRequest, HttpServletRequest request) {
        String name = genGraphByAiRequest.getName();
        String goal = genGraphByAiRequest.getGoal();
        Long userID =  userService.getLoginUser(request).getId();


        String userGoal = "流程图需求:"+goal;
        System.out.println(genGraphByAiRequest.getName());
        String res = aiManager.doChat(userGoal,GraphModelId);

        String[] reslist = res.split("【【【【【");
        if (reslist.length < 3) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"AI响应错误！");
        }
        AIGraphVO aiGraphVO = new AIGraphVO();
        String genGraph = reslist[1];
        String genResult = reslist[2];
        aiGraphVO.setGenGraph(genGraph);
        aiGraphVO.setGenResult(genResult);

        //保存数据到数据库
        Graph graph = new Graph();
        graph.setName(name);
        graph.setUserid(userID);
        graph.setGoal(goal);
        graph.setGengraph(genGraph);
        graph.setGenresult(genResult);

//        Boolean save =  graphService.save(graph);
//        ThrowUtils.throwIf(!save, ErrorCode.SYSTEM_ERROR,"流程图保存失败");

        return ResultUtils.success(graph);
    }
}
