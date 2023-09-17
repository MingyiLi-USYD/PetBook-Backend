package usyd.mingyi.springcloud.service;

import usyd.mingyi.springcloud.entity.*;

public interface SocketService {
    void asyncChatMessageToClient(ChatMessage chatMessage);

    void asyncServiceMessageToClient(ServiceMessage serviceMessage);
    void asyncSystemMessageToClient(SystemMessage systemMessage);


}
