package usyd.mingyi.springcloud.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemMessage extends RequestMessage{
     private SystemMessageType  type; //0是好友上线 //1是好友下线
     private String fromServer;

     public SystemMessage(String fromId, Long date, String toId, SystemMessageType type,String fromServer) {
          super(fromId, date, toId);
          this.fromServer = fromServer;
     }


}
