package cl.ucm.coffee.service.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private String email;
    private String role;

    //para tabla clientes
    private Boolean locked;
    private Boolean disabled;
    private List<String> roles;

}
