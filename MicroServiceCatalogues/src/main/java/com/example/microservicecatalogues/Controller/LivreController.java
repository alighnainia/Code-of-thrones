package com.example.microservicecatalogues.Controller;

import com.example.microservicecatalogues.Entity.Age;
import com.example.microservicecatalogues.Entity.Livre;
import com.example.microservicecatalogues.Entity.Type;
import com.example.microservicecatalogues.Entity.langue;
import com.example.microservicecatalogues.Repository.LivreRepository;
import com.example.microservicecatalogues.Service.IServiceLivre;
import com.example.microservicecatalogues.feign.Demande;
import com.example.microservicecatalogues.feign.IServiceClient;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor

public class LivreController {
    private IServiceLivre iServiceLivre;
    private LivreRepository livreRepository;
    private IServiceClient iServiceClient;
    //writer
    @PostMapping("/AjouterLivre")
    public ResponseEntity<Livre> ajouterLivre (@RequestBody Livre livre, KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return new ResponseEntity<>(iServiceLivre.ajouterLivre(livre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    //writer admin
    @DeleteMapping("/SupprimerLivre/{idLivre}")
    public void deleteLivre( @PathVariable long idLivre, KeycloakAuthenticationToken auth)
    {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        iServiceLivre.deleteLivre(idLivre);
        } else {
            throw new AccessDeniedException("Access denied. User does not have the 'writer' role.");
        }
    }

    //writer

    @PutMapping("/updateLivre/{idLivre}")
    public ResponseEntity<Livre> updateLivre(@RequestBody Livre livre, @PathVariable long idLivre, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return new ResponseEntity<>(iServiceLivre.updateLivre(livre,idLivre),HttpStatus.OK) ;
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/Livres")
    public List<Livre> getLivre()
    {
        return iServiceLivre.getLivre();
    }
//writer
    @PostMapping("/affecterLivreCatalogue/{idLivre}/{idCatalogues}")

    public void affecterLivreCatalogue(@RequestBody Livre livre , @PathVariable long idLivre , @PathVariable long idCatalogues, KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        iServiceLivre.affecterLivreCatalogue(livre,idLivre,idCatalogues);
        } else {
            throw new AccessDeniedException("Access denied. User does not have the 'writer' role.");
        }
    }
    @GetMapping("/LivreParTypeAgeLangue/{age}/{langue}/{type}")
    public List<Livre> findByCatalogues_AgeAndCatalogues_LangueAndCatalogues_Type( @PathVariable Age age, @PathVariable langue langue , @PathVariable Type type){
        return iServiceLivre.findByCatalogues_AgeAndCatalogues_LangueAndCatalogues_Type(age,  langue, type);
    }
    @GetMapping("/LivreParTitre/{titre}")
    Livre getLivreBytitre(@PathVariable String titre){
        return iServiceLivre.getLivreBytitre(titre);
    }

    @GetMapping("/FdemandeLivre")
    public List<Demande> getDemandesAvecDemandeLivre(){
        return iServiceClient.getDemandesAvecDemandeLivre();
    }

//writer
    @PostMapping("/catalogues/{idCatalogues}/livres")
    public ResponseEntity<Livre> ajouetLivreAffecterCatalogue(
            @PathVariable long idCatalogues,
            @RequestBody Livre livre, KeycloakAuthenticationToken auth
    ) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return new ResponseEntity<>(iServiceLivre.ajouetLivreAffecterCatalogue(idCatalogues, livre),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}