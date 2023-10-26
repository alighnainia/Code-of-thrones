package com.example.microservicecoursprive.Service;

import com.example.microservicecoursprive.Entity.Demande;
import com.example.microservicecoursprive.Entity.Matiere;
import com.example.microservicecoursprive.Entity.Niveau;
import com.example.microservicecoursprive.Repository.DemandeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceDemande implements  IServiceDemande{
    private DemandeRepository demandeRepository;

    @Override
    public Demande ajouterDeamnde (Demande demande){
        return demandeRepository.save(demande);
    }

    @Override
    public void deleteDemande( long idDemande) {
        Demande demande = demandeRepository.getReferenceById(idDemande);
        demandeRepository.delete(demande);
    }

    @Override
    public Demande updateDemande (Demande demande, long idDemande){
        Demande demande1 = demandeRepository.findById(idDemande).orElse(null);
        demande1.setNomParent(demande.getNomParent());
        demande1.setNomEleve(demande.getNomEleve());
        demande1.setNumTel(demande.getNumTel());
        demande1.setLocalisation(demande.getLocalisation());
        demande1.setDisponibilite(demande.getDisponibilite());
        demande1.setNiveau(demande.getNiveau());
        demande1.setMatiere(demande.getMatiere());
        demandeRepository.save(demande);
        return demande1 ;
    }

 @Override
 public List<Demande> getDemande() {
     return demandeRepository.findAll();
 }

 @Override
 public  List<Demande>  findDemandeByMatiereAndNiveau(  Matiere matiere, Niveau niveau){
        return demandeRepository.findDemandeByMatiereAndNiveau( matiere, niveau);
 }

 @Override
 public List<Demande> getDemandesAvecDemandeLivre() {
     return demandeRepository.findDemandeByDemandeLivreTrue();
 }










}
