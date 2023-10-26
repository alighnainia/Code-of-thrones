package com.project.pppreclamationmicroservice.Controllers;

import com.project.pppreclamationmicroservice.Responses.BarResponse;
import com.project.pppreclamationmicroservice.Responses.PieResponse;
import com.project.pppreclamationmicroservice.services.DashboardService;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private DashboardService dashboardService;

    //admin

    @GetMapping("/claim/admin")
    public PieResponse pieReclamation(KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
        return  dashboardService.pieReclamation();
        }else {
            throw new AccessDeniedException("Access denied. User does not have the 'admin' role.");
        }
    }
    //admin

    @GetMapping("/claims")
    public BarResponse getClaims(KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
        return  dashboardService.getClaims();
        }else {
            throw new AccessDeniedException("Access denied. User does not have the 'admin' role.");
        }
    }

    //admin

    @GetMapping("/nbClaimByStatusAndUsername/{username}")
    public PieResponse getNbClaimByStatusAndUsername(@PathVariable String username,KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
        return  dashboardService.pieClaimByStatusAndUsername(username);
        } else {
                throw new AccessDeniedException("Access denied. User does not have the 'admin' role.");
            }
    }
    //admin
    @GetMapping("/getNbClaimsByType")
    public PieResponse getNbClaimsByType(KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
        return  dashboardService.getClaimsByType();
        } else {
            throw new AccessDeniedException("Access denied. User does not have the 'admin' role.");
        }
    }


}