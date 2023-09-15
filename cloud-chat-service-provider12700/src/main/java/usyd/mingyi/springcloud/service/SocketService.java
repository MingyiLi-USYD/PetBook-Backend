package usyd.mingyi.springcloud.service;

import usyd.mingyi.springcloud.entity.ChatMessage;
import usyd.mingyi.springcloud.entity.RequestMessage;

public interface SocketService {
    void asyncChatMessageToClient(ChatMessage chatMessage);
}
