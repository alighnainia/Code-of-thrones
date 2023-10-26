package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.Entity.Correction;
import org.example.Entity.Evaluation;
import org.example.Repository.CorrectionRepo;
import org.example.Service.CorrectionServiceI;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/correction")

public class CorrectionC {
    CorrectionServiceI correctionServiceI;

    @Autowired

    private CorrectionRepo correctionRepo;


    @PostMapping("/createCorrection")
    public ResponseEntity<Correction> createCorrection(@RequestBody Correction correction, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return new ResponseEntity<>(correctionServiceI.createCorrection(correction),HttpStatus.OK) ;
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
//writer
    @PutMapping("/modifyCorrection/{idC}")
    public ResponseEntity<Correction> modifyCorrection(@RequestBody Correction modifiedCorrection, @PathVariable Long idC, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return new ResponseEntity<>(correctionServiceI.modifyCorrection(modifiedCorrection, idC),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/correction/{idC}")
    public ResponseEntity<Correction> retrieveCorrection(@PathVariable Long idC, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return new ResponseEntity<>(correctionServiceI.retrieveCorrection(idC),HttpStatus.OK) ;
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

//writer
    @PostMapping("/evaluations/{idE}/corrections")
    public ResponseEntity<Correction> createCorrectionRelatedToEvaluation(
            @PathVariable long idE,
            @RequestBody Correction correction, KeycloakAuthenticationToken auth
    ) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return new ResponseEntity<>(correctionServiceI.createCorrectionRelatedToEvaluation(idE, correction),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/evaluations/{idE}/correction")
    public ResponseEntity<List<Correction>> getCorrectionsByEvaluationId(@PathVariable Long idE) {

        List<Correction> corrections = correctionServiceI.getCorrectionsByEvaluationId(idE);

        if (!corrections.isEmpty()) {
            return ResponseEntity.ok(corrections);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
//writer admin
    @DeleteMapping("/deleteCorrection/{idC}")
    void deleteCorrection(@PathVariable Long idC, KeycloakAuthenticationToken auth) {

        if (correctionRepo.existsById(idC)) {
            Correction correction = correctionRepo.findById(idC).get();
            Evaluation evaluation = correction.getEvaluation();

            // Détacher la correction de l'évaluation
            if (evaluation != null) {
                evaluation.setCorrection(null);
            }

            correctionRepo.deleteById(idC);
        }


    }
}
