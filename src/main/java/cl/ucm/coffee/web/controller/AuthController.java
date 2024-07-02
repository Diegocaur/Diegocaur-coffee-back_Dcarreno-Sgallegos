package cl.ucm.coffee.web.controller;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.persitence.entity.UserRoleEntity;
import cl.ucm.coffee.service.IUserRoleService;
import cl.ucm.coffee.service.dto.LoginDto;
import cl.ucm.coffee.service.dto.UserDTO;
import cl.ucm.coffee.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;

    @Autowired
    private IUserRoleService userRoleService;

//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        try {
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            Authentication authentication = this.authenticationManager.authenticate(login);

            UserEntity userEntity = userRoleService.findByUsername(loginDto.getUsername());
            List<String> rol = userEntity.getRoles().stream().map(UserRoleEntity::getRole).collect(Collectors.toList());


            String jwt = this.jwtUtil.create(loginDto.getUsername(), rol);
            Map<String, String> map = new HashMap<>();
            map.put("token",jwt);

            return ResponseEntity.ok(map);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al intentar iniciar sesion " + e.getMessage());
        }


    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        if (userRoleService.existByUsername(userDTO.getUsername())){
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese nombre");
        }
        try {
            userDTO.setRole("CLIENT");
            UserDTO registrar = userRoleService.crearUsuario(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(registrar);
        }catch (Exception e){
            return ResponseEntity.status(500).body("error al registrar usuario:"+ e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> actualizarUsuario(@RequestBody UserDTO userDTO){
        try {
            UserDTO actualizarUsuario =userRoleService.actualizarUsuario(userDTO);
            return ResponseEntity.ok(actualizarUsuario);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocurrio un error al intentar actualizar los datos: "+ e.getMessage());
        }

    }

    @PutMapping("/block")
    public ResponseEntity<?> blockUser(@RequestParam String username){
        try {
            userRoleService.blockuser(username);
            return ResponseEntity.ok("Usuario " + username + " bloqueado");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error al intentar blockear usuario: "+ e.getMessage());
        }

    }

    @PutMapping("/unlock")
    public ResponseEntity<?> undoUserBlock(@RequestParam String username){
        try {
            userRoleService.undoUserBlock(username);
            return ResponseEntity.ok("Usuario "+ username + " Desbloqueado");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al intentar desbloquear a usuario: "+ e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            return ResponseEntity.ok("Logout queda por parte del cliente: " + token);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error en la peticion " + e.getMessage());
        }

    }






}
