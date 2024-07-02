package cl.ucm.coffee.service;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.persitence.entity.UserRoleEntity;
import cl.ucm.coffee.persitence.repository.UseRoleRepository;
import cl.ucm.coffee.persitence.repository.UserRepository;
import cl.ucm.coffee.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UserRoleService implements IUserRoleService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UseRoleRepository useRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserEntity crearUsuario(UserDTO userDTO) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setLocked(false);
        userEntity.setDisabled(false);

        UserRoleEntity userRoleEntity= new UserRoleEntity();
        userRoleEntity.setUsername(userDTO.getUsername());
        userRoleEntity.setRole(userDTO.getRole());
        userRoleEntity.setGrantedDate(LocalDateTime.now());

        userRepository.save(userEntity);
        useRoleRepository.save(userRoleEntity);

        return userEntity;
    }

    @Override
    public UserEntity actualizarUsuario(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsById(username);
    }


}

