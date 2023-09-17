package usyd.mingyi.springcloud.component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import usyd.mingyi.springcloud.entity.SystemMessage;
import usyd.mingyi.springcloud.entity.SystemMessageType;
import usyd.mingyi.springcloud.service.ChatServiceFeign;


@Component
@Slf4j
public class EventListener {
    @Autowired
    @Qualifier("redisDecorator")
    private CacheManager clientCache;

    @Autowired
    private ChatServiceFeign chatServiceFeign;



    /**
     * 客户端连接
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        String token = client.getHandshakeData().getSingleUrlParam("token");
        if(!validate(client,userId,token)){
            return;
        }
        //beforeSaveToCache(userId);
        processSavingToCache(client,userId);
        //afterSaveToCache(userId);
    }



    /**
     * 客户端断开
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String reason = client.get("disconnectReason");
    }



    public boolean validate(SocketIOClient client,String userId,String token){
/*        if (token != null && JWTUtils.verifyInSocket(token)) {
            DecodedJWT tokenInfo = JWTUtils.getTokenInfo(token);
            Long tokenUserId = tokenInfo.getClaim("userId").asLong();
            if (!userId.equals(String.valueOf(tokenUserId))) {
                client.sendEvent("invalidTokenEvent", new ResponseMessage<>(0, "invalidToken", null));
                clientCache.disconnectClient(client,ClientCache.TOKEN_ISSUE);
                log.info("连接请求被拒绝");
                return false;
            }
        } else {
            client.sendEvent("invalidTokenEvent", new ResponseMessage<>(0, "invalidToken", null));
            clientCache.disconnectClient(client,ClientCache.TOKEN_ISSUE);
            log.info("连接请求被拒绝");
            return false;
        }*/
        return true;
    }
/*    public void beforeSaveToCache (String userId){
        log.info("在保存之前");
        //删除本地已经有的连接
        if(clientCache.hasUserClient(userId)){
            SocketIOClient userClient = clientCache.getUserClient(userId);
            clientCache.disconnectClient(userClient,ClientCache.RE_LOGIN);
            clientCache.deleteUserClient(userId);
        }
        //通知其他服务器让此用户下线
        realTimeService.remindOtherServers(new SystemMessage(userId,System.currentTimeMillis(),null,"ServerA"));
    }*/
    public void processSavingToCache(SocketIOClient client,String userId){
        clientCache.saveUserClient(userId,client);
    }

    public void afterSaveToCache(String userId){
        //通知好友上线
        SystemMessage systemMessage = new SystemMessage(userId, System.currentTimeMillis(),null,SystemMessageType.FRIEND_ONLINE,null);
        chatServiceFeign.sendSystemMessage(systemMessage);
    }

}