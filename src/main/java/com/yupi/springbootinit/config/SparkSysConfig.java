package com.yupi.springbootinit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.briqt.spark4j.model.SparkMessage;

@Configuration
public class SparkSysConfig {

    @Bean
    public SparkMessage systemMessage() {
        return SparkMessage
                .systemContent("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容\n" +
                "\n" +
                "分析需求:\n" +
                "\n" +
                "{数据分析的需求或者目标}\n" +
                "\n" +
                "图表类型:\n" +
                "\n" +
                "{生成图标的类型，可以是折线图 柱状图 雷达图 饼图}\n" +
                 "\n" +
                "原始数据:\n" +
                "\n" +
                "{csv格式的原始数据，用,作为分隔符}\n" +
                "\n" +
                "请根据这三部分内容，按照以下指定格式生成返回数据,使用】】】】】分割，（此外不要输出任何多余的开头，结尾，注释）\n" +
                "】】】】】\n" +
                "\n" +
                "{前端 Echarts V5的option配置对象的json格式代码，合理地将数据进行可视化，不要使用'这个字符，不要输出任何多余的内容，比如注释}" +
                "\n" +
                "】】】】】" +
                "\n" +
                "{明确的数据分析结论，越详细越好，不要生成多余的注释}");
    }
}
