package org.example.Controller;


import lombok.AllArgsConstructor;
import org.example.Entity.Correction;
import org.example.Entity.Evaluation;
import org.example.Entity.ExportRequest;
import org.example.Repository.EvaluationRepo;
import org.example.Service.EvaluationService;
import org.example.Service.EvaluationServiceI;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/evaluation")
public class EvaluationC {

    EvaluationServiceI evaluationServiceI;

    @Autowired

    private EvaluationRepo evaluationRepo;

//writer
    @PostMapping("/createEvaluation")
     public ResponseEntity<Evaluation> createEvaluation(@RequestBody Evaluation evaluation, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return new ResponseEntity<>(evaluationServiceI.createEvaluation(evaluation),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
//writer
    @PutMapping("/modifyEvaluation/{idE}")
    public ResponseEntity<Evaluation> modifyEvaluation(@RequestBody Evaluation modifiedEvaluation,@PathVariable Long idE, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return new ResponseEntity<>(evaluationServiceI.modifyEvaluation(modifiedEvaluation, idE),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/evaluation/{idE}")
    public Evaluation retrieveEvaluation(@PathVariable Long idE, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        return evaluationServiceI.retrieveEvaluation(idE);
        }else {
            throw new AccessDeniedException("Access denied. User does not have the 'writer' role.");
        }
    }
    //writer admin
    @DeleteMapping("/deleteEvaluation/{idE}")
    void deleteEvaluation(@PathVariable Long idE, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("writer");
        if (hasUserRole) {
        evaluationServiceI.deleteEvaluation(idE);
        }else {
            throw new AccessDeniedException("Access denied. User does not have the 'writer' role.");
        }
    }
    @GetMapping("/filter")
    public List<Evaluation> getEvaluationsByFilter(@RequestParam String filterType) {
        return evaluationServiceI.getEvaluationsByFilter(filterType);
    }
    @GetMapping("/export/csv/{idE}")
    public void exportToCSV(@PathVariable Long idE, HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=evaluation_" + idE + ".csv");

        try {
            evaluationServiceI.exportToCSV(idE, response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/evaluations/{IdE}/view-correction")
    public String viewCorrection(@PathVariable Long idE, Model model) {
        Optional<Correction> optionalCorrection = evaluationRepo.getCorrectionByIdE(idE);

        optionalCorrection.ifPresent(correction -> {
            model.addAttribute("correction", correction);
        });

        return "view_correction";
    }

}
