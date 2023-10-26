package com.project.pppreclamationmicroservice.Controllers;


import com.project.pppreclamationmicroservice.Entity.Attachment;
import com.project.pppreclamationmicroservice.Responses.MessageResponse;
import com.project.pppreclamationmicroservice.services.IServiceAttachment;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/upload")
public class AttachmentController {

    private IServiceAttachment iServiceAttachment;

    //admin
    @GetMapping("/findByClaim/{id}")
    public ResponseEntity<List<Attachment>> findByClaim(@PathVariable Long id, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("admin");
        if (hasUserRole) {
        return new ResponseEntity<>(iServiceAttachment.findByClaim(id),HttpStatus.OK) ;
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping("/uploadFile/{id}")
    public ResponseEntity<MessageResponse>  upload(@RequestParam("files") List<MultipartFile> files, @PathVariable Long id) {
        MessageResponse message =    iServiceAttachment.uploadFile(files,id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = iServiceAttachment.download(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }




}