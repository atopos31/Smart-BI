package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.service.SparkService;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.exception.SparkException;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SparkServiceImpl implements SparkService {
    @Resource
    private SparkClient sparkClient;

    @Resource
    private SparkMessage systemMessage; // Inject the system message bean

    @Override
    public String communicateWithSpark(String userMessage) {
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(systemMessage); // Use the injected system message
        messages.add(SparkMessage.userContent("分析需求:\n" +
                "\n" +
                "{网站访问数据分析}\n" +
                "\n" +
                "分析目标:\n" +
                "\n" +
                "{折线图}\n" +
                "\n" +
                "原始数据:\n" +
                "\n" +
                "{日期,用户数\\n1号,10\\n2号,20\\n3号,30\\n4号,20\\n5号,25\\n6号,10\\n7号,15\\n}"));
        messages.add(SparkMessage.assistantContent("\n" +
                "】】】】】\n" +
                "{\n" +
                "\"title\": {\n" +
                "\"text\": \"每日用户访问量分析\",\n" +
                "\"subtext\": \"数据来源：网站访问统计\"\n" +
                "},\n" +
                "\"tooltip\": {\n" +
                "\"trigger\": \"axis\",\n" +
                "\"axisPointer\": {\n" +
                "\"type\": \"shadow\"\n" +
                "}\n" +
                "},\n" +
                "\"legend\": {\n" +
                "\"data\": [\"用户数\"]\n" +
                "},\n" +
                "\"xAxis\": [\n" +
                "{\n" +
                "\"type\": \"category\",\n" +
                "\"data\": [\"1号\", \"2号\", \"3号\", \"4号\", \"5号\", \"6号\", \"7号\"],\n" +
                "\"axisTick\": {\n" +
                "\"alignWithLabel\": true\n" +
                "}\n" +
                "}\n" +
                "],\n" +
                "\"yAxis\": [\n" +
                "{\n" +
                "\"type\": \"value\"\n" +
                "}\n" +
                "],\n" +
                "\"series\": [\n" +
                "{\n" +
                "\"name\": \"用户数\",\n" +
                "\"type\": \"bar\",\n" +
                "\"barWidth\": \"60%\",\n" +
                "\"data\": [10, 20, 30, 20, 25, 10, 15]\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "】】】】】" +
                "根据提供的数据，我们可以看到3号的用户访问量最高，达到了30人，而6号的用户访问量最低，为10人。整体来看，用户访问量在20人左右"));
        messages.add(SparkMessage.userContent(userMessage));

        SparkRequest sparkRequest = SparkRequest.builder()
                .messages(messages)
                .apiVersion(SparkApiVersion.V3_5)
                .build();

        try {
            SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
            return chatResponse.getContent();
        } catch (SparkException e) {
            System.out.println("An exception occurred: " + e.getMessage());
            return null;
        }
    }
}
