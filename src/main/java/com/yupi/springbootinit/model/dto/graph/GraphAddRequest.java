package com.yupi.springbootinit.model.dto.graph;

import lombok.Data;

import java.io.Serializable;

@Data
public class GraphAddRequest implements Serializable {
    /**
     * 流程图名称
     */
    private String name;

    /**
     * 目标需求
     */
    private String goal;

    /**
     * 生成的流程图数据
     */
    private String gengraph;

    /**
     * 生成的文字说明
     */
    private String genresult;
}
