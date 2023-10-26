package com.project.authentification.Controller;

import com.project.authentification.Payload.Request.KeycloakUser;
import com.project.authentification.Service.KeyCloakService;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping( "/keycloakUser")
//@CrossOrigin("*")
public class KeyCloakController {

    KeyCloakService service;
    @PostMapping("/signup")

    public UserRepresentation addUser(@Valid @RequestBody KeycloakUser keycloakUser){

        return service.addUser(keycloakUser);
    }

    @PostMapping("/admin/addUserByAdmin")
    public ResponseEntity<String> addUserByAdmin(@Valid @RequestBody KeycloakUser keycloakUser){
        service.addUserByAdmin(keycloakUser);
        return ResponseEntity.status(HttpStatus.OK).body("User Added Successfully");
    }



    @GetMapping("/admin/details/{userId}")
    public UserRepresentation getUser(@PathVariable("userId") String userId){
        UserRepresentation user = service.getUser(userId);
        return user;
    }

    @GetMapping("/admin/findAll")
    public List<UserRepresentation> getUsers(){
        List<UserRepresentation> user = service.getUsers();
        return user;
    }
    @PutMapping("/admin/addRoleToUser/{userName}")
    public String assignRealmRolesToUser(@RequestBody KeycloakUser keycloakUser, @PathVariable("userName") String userName){
    service.assignRealmRolesToUser(keycloakUser,userName);
    return "role assigned to user";
    }

    @PutMapping( "/admin/UpdateUserByAdmin/{userId}")
    public String updateUserByAdmin(@Valid @PathVariable("userId") String userId, @RequestBody KeycloakUser keycloakUser){

        service.updateUserByAmin(userId, keycloakUser);
        return "User Details Updated Successfully.";
    }
    @PutMapping( "/UpdateUser")
    public String updateUser(@Valid @RequestBody KeycloakUser keycloakUser,KeycloakAuthenticationToken authenticationToken){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) authenticationToken.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String userId = context.getToken().getSubject();
        System.out.println(userId);
        service.updateUser(userId, keycloakUser);
        return "User Details Updated Successfully.";
    }

    @DeleteMapping( "/deleteAccount/{userId}")
    public String deleteUserByAdmin(@PathVariable("userId") String userId){

        service.deleteUser(userId);
        return "User Deleted Successfully.";
    }
    @DeleteMapping( "/deleteMyAccount")
    public String deleteUser(KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String userId = context.getToken().getSubject();
        service.deleteMyAccount(userId);
        return "User Deleted Successfully.";
    }

    @GetMapping( "/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId){
        service.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @GetMapping( "/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){
        service.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }
    @GetMapping("/hello")
    public String hello (KeycloakAuthenticationToken auth){
            KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
            KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
            String username = context.getToken().getPreferredUsername();
            String email = context.getToken().getEmail();
            String userId=context.getToken().getSubject();
            return "Hello, " + username + " (" + email + ")" + userId ;

    }
    @GetMapping("/jwt")
    @ResponseBody
    public Map<String,String> map (HttpServletRequest request){
        KeycloakAuthenticationToken token=(KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal= (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext keycloakSecurityContext=principal.getKeycloakSecurityContext();
        Map<String,String> map=new HashMap<>();
        map.put("access_token",keycloakSecurityContext.getTokenString());
        return map;
    }
    @PutMapping ("admin/roles/{userId}")
    public ResponseEntity<String> assignRoleToUser(@PathVariable("userId") String userId,
                                                   @RequestParam("roleName") String roleName) {
        try {
            service.assignRoleToUser(userId, roleName);
            return ResponseEntity.ok("Role assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign role");
        }

    }




}
