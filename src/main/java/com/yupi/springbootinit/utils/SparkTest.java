package com.yupi.springbootinit.utils;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.exception.SparkException;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkTextUsage;

import java.util.ArrayList;
import java.util.List;

public class SparkTest {
    public static void main(String[] args) {
        SparkClient sparkClient = new SparkClient();

        // 设置认证信息
        sparkClient.appid = "fa7af321";
        sparkClient.apiKey = "2ec08699d411fc8c39c903511eb902ee";
        sparkClient.apiSecret = "MTUyYTIyYTk0NTkxNjcyNzY0M2I5NjAw";
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage
                .systemContent("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容\\n\\n分析需求:\\n\\n{数据分析的需求或者目标}\\n\\n原始数据:\\n\\n{csv格式的原始数据，用,作为分隔符}\\n\\n请根据这两部分内容，按照以下指定格式生成内容,必须使用【【【【【【分割，（此外不要输出任何多余的开头，结尾，注释）\\n【【【【【\\n\\n{前端 Echarts V5的option配置对象的json格式代码，合理地将数据进行可视化，不要使用'这个字符，不要输出任何多余的内容，比如注释}\\n\\n【【【【【\\n\\n{明确的数据分析结论，越详细越好，不要生成多余的注释}\\n\\n\\n\\n"));
        messages.add(SparkMessage
                .userContent("分析需求:\n\n{网站访问数据分析}\n\n原始数据:\n\n{日期,用户数\n1号,10\n2号,20\n3号,30\n4号,20\n5号,25\n6号,10\n7号,15\n}"));
// 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
// 消息列表
                .messages(messages)
// 模型回答的tokens的最大长度,非必传，默认为2048。
// V1.5取值为[1,4096]
// V2.0取值为[1,8192]
// V3.0取值为[1,8192]
                .maxTokens(2048)
// 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
// 指定请求版本，默认使用最新3.0版本
                .apiVersion(SparkApiVersion.V3_5).build();

        try {
            // 同步调用
            SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
            SparkTextUsage textUsage = chatResponse.getTextUsage();

            System.out.println("\n回答：" + chatResponse.getContent());
            System.out.println("\n提问tokens：" + textUsage.getPromptTokens() + "，回答tokens：" + textUsage.getCompletionTokens() + "，总消耗tokens：" + textUsage.getTotalTokens());
        } catch (SparkException e) {
            System.out.println("发生异常了：" + e.getMessage());
        }

    }
}
