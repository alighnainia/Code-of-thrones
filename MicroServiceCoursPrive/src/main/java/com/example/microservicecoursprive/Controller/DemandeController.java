package com.example.microservicecoursprive.Controller;

import com.example.microservicecoursprive.Entity.Demande;
import com.example.microservicecoursprive.Entity.Matiere;
import com.example.microservicecoursprive.Entity.Niveau;
import com.example.microservicecoursprive.Service.IServiceDemande;
import lombok.AllArgsConstructor;
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
public class DemandeController {
private IServiceDemande iServiceDemande;
//parent
@PostMapping("/AjouterDemande")
public ResponseEntity<Demande> ajouterDeamnde (@RequestBody Demande demande, KeycloakAuthenticationToken auth) {
    KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
    KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
    boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("parent");
    if (hasUserRole) {
    return new ResponseEntity<>(iServiceDemande.ajouterDeamnde(demande),HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

//parent admin
    @DeleteMapping("/SupprimerDemande/{idDemande}")
    public void deleteDemande( @PathVariable long idDemande, KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("parent");
        if (hasUserRole) {
            iServiceDemande.deleteDemande(idDemande);
        } else {
            throw new AccessDeniedException("Access denied. User does not have the 'admin' role.");
        }
    }

//parent
    @PutMapping("/updateDemande/{idDemande}")
    public ResponseEntity<Demande> updateDemande (@RequestBody Demande demande, @PathVariable long idDemande, KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("parent");
        if (hasUserRole) {
            return new ResponseEntity<>( iServiceDemande.updateDemande(demande,idDemande),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
 @GetMapping("/LesDemandes")
 public List<Demande> getDemande(){
    return iServiceDemande.getDemande();
 }
 @GetMapping("/DemandeParMatiereNiveau/{matiere}/{niveau}")
 public  List<Demande>  findDemandeByMatiereAndNiveau(  @PathVariable Matiere matiere, @PathVariable Niveau niveau){
    return iServiceDemande.findDemandeByMatiereAndNiveau( matiere, niveau);
 }
 @GetMapping("/demandeLivre")
    public List<Demande> getDemandesAvecDemandeLivre(){
 return iServiceDemande.getDemandesAvecDemandeLivre();

 }

















}

