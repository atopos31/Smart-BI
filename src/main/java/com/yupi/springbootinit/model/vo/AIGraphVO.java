package com.yupi.springbootinit.model.vo;

import lombok.Data;

@Data
public class AIGraphVO {
    /**
     * 生成的流程图数据
     */
    private String genGraph;

    /**
     * 生成的文字说明
     */
    private String genResult;
}
