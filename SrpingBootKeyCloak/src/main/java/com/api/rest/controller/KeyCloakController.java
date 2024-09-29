package com.api.rest.controller;

import com.api.rest.controller.dto.UserDTO;
import com.api.rest.service.IKeyCloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/keycloak/user")
@PreAuthorize("hasRole('admin_client_role')")
public class KeyCloakController {

    @Autowired
    private IKeyCloakService keyCloakService;

    @GetMapping("/search")
    public ResponseEntity<?> findAllUsers(){
        return ResponseEntity.ok(keyCloakService.findAllUsers());
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<?> findAllUsers(@PathVariable String username){
        return ResponseEntity.ok(keyCloakService.searchUserByUsername(username));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        String response = keyCloakService.createUser(userDTO);
        return ResponseEntity.created(new URI("/keycloak/user/create")).body(response);
    }

    @PutMapping ("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO){
        keyCloakService.updateUser(userId, userDTO);
        return ResponseEntity.ok("User updated successfully!!");
    }

    @DeleteMapping ("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        keyCloakService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}














