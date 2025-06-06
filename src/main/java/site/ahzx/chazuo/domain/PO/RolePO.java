package site.ahzx.chazuo.domain.PO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePO {
    private Integer id;
    private String name;
    private String code;
    private String description;
    private Date createdTime;
    private Date updatedTime;
}
