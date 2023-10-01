package usyd.mingyi.common.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @JsonIgnore // 忽略序列化该字段
    String id;
    Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Set<Long> lovedComments = new HashSet<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Set<Long> lovedSubcomments = new HashSet<>();
}
