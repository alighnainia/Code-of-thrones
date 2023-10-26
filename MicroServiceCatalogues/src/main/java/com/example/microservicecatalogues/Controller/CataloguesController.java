package com.example.microservicecatalogues.Controller;

import com.example.microservicecatalogues.Entity.Catalogues;
import com.example.microservicecatalogues.Repository.CataloguesRepository;
import com.example.microservicecatalogues.Service.IServiceCatalogues;
import com.example.microservicecatalogues.feign.Demande;
import com.example.microservicecatalogues.feign.IServiceClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@Controller
@RequestMapping
public class CataloguesController {
private IServiceCatalogues iServiceCatalogues;
private IServiceClient iServiceClient;
@PostMapping("/AjouterCatalogue")
    public ResponseEntity<Catalogues> ajouterCatalogue(@RequestBody Catalogues catalogues, KeycloakAuthenticationToken auth){
    KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
    KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
    boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
    if (hasUserRole) {
return new ResponseEntity<>(iServiceCatalogues.ajouterCatalogue(catalogues),HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    }
@DeleteMapping("/SupprimerCatalogue/{idCatalogues}")
public void deleteCatalogues(@PathVariable long idCatalogues, KeycloakAuthenticationToken auth){
    KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
    KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
    boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
    if (hasUserRole) {
iServiceCatalogues.deleteCatalogues(idCatalogues);
    } else {
        throw new AccessDeniedException("Access denied. User does not have the 'writer' role.");
    }
}

    @PutMapping("/UpdateCatalogue/{idCatalogues}")
    public ResponseEntity<Catalogues> updateCatalogue(@RequestBody Catalogues catalogues, @PathVariable long idCatalogues, KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
            return new ResponseEntity<>(iServiceCatalogues.updateCatalogue(catalogues,idCatalogues),HttpStatus.OK) ;
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

@GetMapping("/Catalogues")
public List<Catalogues> getCatalogues(){
return iServiceCatalogues.getCatalogues();
}

@GetMapping("/FLesDemandes")
public List<Demande> getDemande(){
    return iServiceClient.getDemande();
}


}
