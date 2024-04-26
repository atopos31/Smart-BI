package com.yupi.springbootinit.model.dto.graph;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenGraphByAiRequest implements Serializable {
    /**
     * 流程图名称
     */
    private String name;
    /**
     * 绘制目标
     */
    private String goal;

    private static final long serialVersionUID = 1L;
}
