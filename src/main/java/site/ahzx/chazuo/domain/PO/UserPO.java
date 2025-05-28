package site.ahzx.chazuo.domain.PO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPO {
    private Integer id;
    private String openid;
    private String unionid;
    private String sessionKey;
    private String nickname;
    private String phone;
    private Date createdTime;
}
