package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.service.dto.UserDTO;

public interface IUserRoleService {
    UserDTO crearUsuario(UserDTO userDTO);
    UserDTO actualizarUsuario(UserDTO userDTO);
    UserEntity findByUsername(String username);
    boolean existByUsername(String username);

    void blockuser(String username);
    void undoUserBlock(String username);
}
