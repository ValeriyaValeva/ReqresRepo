package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequestPojo {
    private String username;
    private String password;
}

