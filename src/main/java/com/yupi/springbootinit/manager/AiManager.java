package com.yupi.springbootinit.manager;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Component
public class AiManager {

    @Resource
    public YuCongMingClient client;

    public static Long ModelId = 1782007708766347266L;

    public String doChat(String msg) {
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(ModelId);
        devChatRequest.setMessage(msg);
        BaseResponse<DevChatResponse> response = client.doChat(devChatRequest);
        if (response == null) {
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR,"AI 接口请求错误！");
        }
        return response.getData().getContent();
    }

}
