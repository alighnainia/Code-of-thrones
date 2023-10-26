package com.project.pppreclamationmicroservice.Controllers;


import com.project.pppreclamationmicroservice.DTO.ClaimDto;
import com.project.pppreclamationmicroservice.Entity.Claim;
import com.project.pppreclamationmicroservice.Entity.Status;
import com.project.pppreclamationmicroservice.Entity.TypeReclamation;
import com.project.pppreclamationmicroservice.services.IServiceClaim;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.boot.SpringBootExceptionReporter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/claim")
@CrossOrigin("*")
@Slf4j
public class ClaimController {
    private IServiceClaim iServiceRec;



    @PostMapping("/addClaim")
    public Claim addReclamation(@RequestBody Claim request, KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        String email = context.getToken().getEmail();
        Map<String, Object> model = new HashMap<>();
        model.put("email",email);
        request.setDateDiff(request.getDateDiff());
        request.setDateRes(request.getDateRes());
        model.put("title",request.getTitle());
        model.put("typeReclamation",request.getTypeReclamation());
        model.put("status",request.getStatus());
        model.put("description",request.getDescription());
        model.put("username",username);
        request.setEmail(email);
        request.setUsername(username);
        return iServiceRec.addClaim(request);
    }


    @GetMapping("/allClaims")
    public List<Claim> getAllReclamations () {
        return iServiceRec.findAllClaims();
    }

    @GetMapping("/findClaim/{id}")
    public Claim getReclamationById (@PathVariable("id") Long id) {
        return iServiceRec.findClaimById(id);
    }


    @PutMapping("/changeStatus")
    public ResponseEntity<Claim> changeStatus(@RequestBody Claim claim, KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
        return new ResponseEntity<> (iServiceRec.changeStatus(claim), HttpStatus.OK);
        } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
    }
    @PutMapping("/changeAnyStatus/{id}")
    public ResponseEntity<Claim> changeAnyStatus(@RequestBody ClaimDto claimDto,@PathVariable Long id, KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
            return new ResponseEntity<> ( iServiceRec.changeAnyStatus(id,claimDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping("/cancelClaim/{id}")
    public void cancelReclamation(@PathVariable Long id) {
        iServiceRec.cancelClaim(id);
    }


    @DeleteMapping("/removeClaim/{id}")
    public void removeReclamation(@PathVariable Long id){
        iServiceRec.removeClaim(id);
    }

    @GetMapping("/nbClaimsResolu/{d1}/{d2}")
    public  ResponseEntity<Integer> nbReclamationsResolu(@PathVariable ("d1") @DateTimeFormat(pattern = "YYYY-MM-DD") Date dateDiff, @PathVariable ("d2") @DateTimeFormat(pattern = "YYYY-MM-DD") Date dateRes, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
        return new ResponseEntity<> (iServiceRec.nbClaimsResolu(dateDiff,dateRes), HttpStatus.OK);
        }else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
    }


    @PutMapping("/resoudreClaim/{id}")
    public Claim resoudreClaim(@PathVariable Long id){
        return iServiceRec.resolveStatusClaim(id);
    }

    @PutMapping("/retourStatus/{id}")
    public Claim returnStatusClaim(@PathVariable Long id){
        return iServiceRec.returnStatusClaim(id);
    }


    @PutMapping("/archiveStatus/{id}")
    public Claim archiveStatusClaim(@PathVariable Long id){
        return iServiceRec.archiveStatusClaim(id);
    }

    @PutMapping("/modifier")
    public Claim modifier(@RequestBody Claim claim){
        return iServiceRec.editClaim(claim);
    }
    //admin
    @GetMapping("/findAllByStatusNotAnnuler")
    public ResponseEntity<List<Claim>> getAllReclamationsByStatus ( KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        List<Claim> reclamations  = iServiceRec.findReclamationByStatus();
        if (hasUserRole) {
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/findByDateDiff/{d1}/{d2}")
    public List<Claim> findByDateDiff(@PathVariable ("d1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable ("d2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return iServiceRec.findByDateDiff(startDate,endDate);
    }

    @GetMapping("/findByDateRes/{d1}/{d2}")
    public List<Claim> findByDateRes(@PathVariable ("d1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable ("d2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return iServiceRec.findByDateRes(startDate,endDate);
    }

    @GetMapping("/searchByKeywords")
    public List<Claim> search(String keywords) {
        return iServiceRec.search(keywords);

    }

    @GetMapping("/getClaimsByUsername/{username}")
    public ResponseEntity<List<Claim>> getClaimsByUsername (@PathVariable String username) {

        List<Claim> reclamations = iServiceRec.findByUsername(username);

        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }



    @GetMapping("/findClaimsByTypeReclamation/{type}")
    public List<Claim> findClaimsByTypeReclamation(@PathVariable TypeReclamation type) {
        return iServiceRec.findClaimsByTypeReclamation(type);
    }
    //admin
    @DeleteMapping("/deleteClaimResolu")
    public void deleteClaimByStatusResolu(KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
            iServiceRec.deleteClaimByStatusResolu();
        }
    }


    @GetMapping("/sendMessageNotif")
    public String Noticate() {
        return iServiceRec.Noticate();
    }

    //admin

    @GetMapping("/countDaysBetweenDateDiffAndNow/{id}")
    public long daysBetween(@PathVariable Long id,KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
        return iServiceRec.getDaysBetween(id);
        }else {
            throw new AccessDeniedException("Access denied. User does not have the 'admin' role.");
        }
    }
    //admin
    @GetMapping("/findClaimsByStatus/{status}")
    public ResponseEntity<List<Claim>> findClaimsByStatus(@PathVariable Status status,KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
        return new ResponseEntity<>(iServiceRec.findClaimsByStatus(status),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    //admin
    @GetMapping("/findHistoric")
    public ResponseEntity<List<Claim>> findHistorique(KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
            return new ResponseEntity<>(iServiceRec.findHistory(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

















}
