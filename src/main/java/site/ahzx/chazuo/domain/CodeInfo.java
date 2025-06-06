package site.ahzx.chazuo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeInfo {
    private String code;
    private long expireTime;
}
