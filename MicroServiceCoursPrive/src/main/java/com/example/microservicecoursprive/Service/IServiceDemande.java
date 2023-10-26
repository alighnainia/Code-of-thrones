package com.example.microservicecoursprive.Service;

import com.example.microservicecoursprive.Entity.Demande;
import com.example.microservicecoursprive.Entity.Matiere;
import com.example.microservicecoursprive.Entity.Niveau;

import java.util.List;

public interface IServiceDemande {
    public Demande ajouterDeamnde (Demande demande);
    public void deleteDemande( long idDemande) ;
    public Demande updateDemande (Demande demande, long idDemande);
    public List<Demande> getDemande();
    List<Demande>  findDemandeByMatiereAndNiveau(  Matiere matiere, Niveau niveau);
    public List<Demande> getDemandesAvecDemandeLivre();



}
