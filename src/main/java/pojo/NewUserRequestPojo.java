package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NewUserRequestPojo {
    private String name;
    private String job;

}
