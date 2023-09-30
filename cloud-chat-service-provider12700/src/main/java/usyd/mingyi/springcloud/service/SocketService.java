package usyd.mingyi.springcloud.service;


import usyd.mingyi.common.entity.ChatMessage;
import usyd.mingyi.common.entity.ServiceMessage;
import usyd.mingyi.common.entity.SystemMessage;

public interface SocketService {
    void asyncChatMessageToClient(ChatMessage chatMessage);

    void asyncServiceMessageToClient(ServiceMessage serviceMessage);
    void asyncSystemMessageToClient(SystemMessage systemMessage);


}
