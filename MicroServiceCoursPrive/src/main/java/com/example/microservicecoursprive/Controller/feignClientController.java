package com.example.microservicecoursprive.Controller;

import com.example.microservicecoursprive.Entity.Demande;
import com.example.microservicecoursprive.Entity.Matiere;
import com.example.microservicecoursprive.Entity.Niveau;
import com.example.microservicecoursprive.Service.IServiceDemande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/feignService")
public class feignClientController {
        @Autowired
        private IServiceDemande iServiceClient;
        @GetMapping("/FLesDemandes")
        public List<Demande> getDemande(){
            return iServiceClient.getDemande();
        }
        @GetMapping("/FDemandeParMatiereNiveau/{matiere}/{niveau}")
        public  List<Demande>  findDemandeByMatiereAndNiveau(@PathVariable Matiere matiere, @PathVariable Niveau niveau){
            return iServiceClient.findDemandeByMatiereAndNiveau( matiere, niveau);
        }
        @GetMapping("/FdemandeLivre")
        public List<Demande> getDemandesAvecDemandeLivre(){
        return iServiceClient.getDemandesAvecDemandeLivre();
         }




}


