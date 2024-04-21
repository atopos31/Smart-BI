package com.yupi.springbootinit.model.vo;

import lombok.Data;

@Data
public class AIChartVO {
    /**
     * 生成的图表数据
     */
    private String genChart;
    /**
     * 生成的分析结论
     */
    private String genResult;
}
