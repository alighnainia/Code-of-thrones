package com.example.microservicecoursprive.Repository;

import com.example.microservicecoursprive.Entity.Demande;
import com.example.microservicecoursprive.Entity.Matiere;
import com.example.microservicecoursprive.Entity.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande,Long > {
    List<Demande>  findDemandeByMatiereAndNiveau(  Matiere matiere, Niveau niveau);
    List<Demande>findDemandeByDemandeLivreTrue();
}
