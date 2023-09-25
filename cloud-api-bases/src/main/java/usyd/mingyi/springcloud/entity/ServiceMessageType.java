package usyd.mingyi.springcloud.entity;


public enum ServiceMessageType {
    DELETE_FRIEND(),
    ADD_FRIEND(),
    AGREE_ADD_FRIEND(),
    REJECT_ADD_FRIEND(),
    FRIEND_ONLINE(),
    FRIEND_OFFLINE(),
    NEW_COMMENT(),
    NEW_LIKE(),
    MENTION();

}
