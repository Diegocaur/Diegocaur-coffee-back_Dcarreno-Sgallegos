package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.service.dto.UserDTO;

public interface IUserRoleService {
    UserEntity crearUsuario(UserDTO userDTO);
    UserEntity actualizarUsuario(UserDTO userDTO);

}
