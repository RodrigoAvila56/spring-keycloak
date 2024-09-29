package com.api.rest.service.impl;

import com.api.rest.controller.dto.UserDTO;
import com.api.rest.service.IKeyCloakService;
import com.api.rest.util.KeyCloakProvider;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class KeyCloakServiceImpl implements IKeyCloakService {


    /**
     * Metodo para listar todos los usuarios de Keycloak
     * @return List<UserRepresentation>
     */
    @Override
    public List<UserRepresentation> findAllUsers() {
        return KeyCloakProvider.getRealmResource().users().list();
    }

    /**
     * Metodo para buscar un usuario por el username
     * @param username
     * @return List<UserRepresentation>
     */

    @Override
    public List<UserRepresentation> searchUserByUsername(String username) {
        return KeyCloakProvider.getRealmResource().users().searchByUsername(username, true);
    }


    /**
     * Metodo para crear un usuario nuevo en keycloak
     * @param userDTO
     * @return String
     */

    @Override
    public String createUser(@NotNull UserDTO userDTO) {

        int status = 0;
        UsersResource userResource = KeyCloakProvider.getUserResource();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDTO.getFirtName());
        userRepresentation.setLastName(userDTO.getLastName());
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        Response response = userResource.create(userRepresentation);
        status = response.getStatus();

        if (status == 201) {
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(OAuth2Constants.PASSWORD);
            credentialRepresentation.setValue(userDTO.getPassword());

            userResource.get(userId).resetPassword(credentialRepresentation);

            RealmResource realmResource = KeyCloakProvider.getRealmResource();

            List<RoleRepresentation> roleRepresentations = null;
            if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
                roleRepresentations = List.of(realmResource.roles().get("user").toRepresentation());
            } else {
                roleRepresentations = realmResource.roles()
                        .list()
                        .stream()
                        .filter(role -> userDTO.getRoles()
                                .stream()
                                .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                        .toList();
            }
            realmResource.users().get(userId).roles().realmLevel().add(roleRepresentations);
            return "User created successfully";
        }else if (status == 401) {
            log.error("User exist already");
            return "User exist already";
        }else {
            log.error("Error creating user, please contact with the administrator");
            return "User exist already";
        }
    }

    /**
     * Metodo para borrar un usuario en keyCloak
     * @return void
     */
    @Override
    public void deleteUser(String userId) {
        KeyCloakProvider.getUserResource().get(userId).remove();
    }

    /**
     *  Metodo para actualizar un usuario en keyCloak
     * @return void
     */
    @Override
    public void updateUser(String userId,@NotNull UserDTO userDTO) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setValue(userDTO.getPassword());

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDTO.getFirtName());
        userRepresentation.setLastName(userDTO.getLastName());
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        UserResource userResource = KeyCloakProvider.getUserResource().get(userId);
        userResource.update(userRepresentation);

    }
}
