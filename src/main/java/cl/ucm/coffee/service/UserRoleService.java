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
    public UserDTO crearUsuario(UserDTO userDTO) {

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

        return userDTO;
    }

    @Override
    public UserDTO actualizarUsuario(UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(userDTO.getUsername())
                .orElseThrow(()->new RuntimeException("El usuario no existe"));
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setEmail(userDTO.getEmail());
        userRepository.save(userEntity);

        return userDTO;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsById(username);
    }

    @Override
    public void blockuser(String username) {
        UserEntity userEntity = userRepository.findById(username).orElseThrow(()-> new RuntimeException("No existe ese usuario"));
        userEntity.setLocked(true);
        userRepository.save(userEntity);
    }

    @Override
    public void undoUserBlock(String username) {
        UserEntity userEntity = userRepository.findById(username).orElseThrow(()-> new RuntimeException("No existe ese usuario"));
        userEntity.setLocked(false);
        userRepository.save(userEntity);
    }


}

