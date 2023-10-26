package com.example.microservicecatalogues.Service;

import com.example.microservicecatalogues.Entity.*;
import com.example.microservicecatalogues.Repository.CataloguesRepository;
import com.example.microservicecatalogues.Repository.LivreRepository;
import com.example.microservicecatalogues.feign.IServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class ServiceLivre implements IServiceLivre {
    private LivreRepository livreRepository;
    private CataloguesRepository cataloguesRepository;
    private IServiceClient iServiceClient;
    @Override
    public Livre ajouterLivre (Livre livre){
        return livreRepository.save(livre);
    }

    @Override
    public void deleteLivre( long idLivre) {
        Livre livre = livreRepository.getReferenceById(idLivre);
        livreRepository.delete(livre);
    }

    @Override
    public Livre updateLivre(Livre livre, long idLivre){
        Livre livre1 = livreRepository.findById(idLivre).orElse(null);
        livreRepository.save(livre);
        return livre1 ;
    }

    @Override
    public List<Livre> getLivre() {
        return livreRepository.findAll();
    }

    @Override
    public void affecterLivreCatalogue( Livre livre ,long idLivre ,  long idCatalogues){
         Catalogues catalogues = cataloguesRepository.findById(idCatalogues).orElse(null);
        Livre livre1 = livreRepository.findById(idLivre).orElse(null);
        livre.setCatalogues(catalogues);

    }
    @Override

   public List<Livre> findByCatalogues_AgeAndCatalogues_LangueAndCatalogues_Type( Age age, langue langue,Type type)

    {
        return livreRepository.findByCatalogues_AgeAndCatalogues_LangueAndCatalogues_Type( age,  langue, type);
    }
    @Override
    public Livre getLivreBytitre(String titre){
        return livreRepository.getLivreBytitre(titre);
    }


    /* @Override
     public void affecterLivrePartypeAgeLangue(Livre livre, String type, String age , String langue){
         Catalogues catalogues = cataloguesRepository.
     }
   @Override
   public void Affecterlivredemande(Long idLivre, Long idDemande){
Demande demande =iServiceClient.getdemandeById(idDemande);
Livre livre = livreRepository.findById(idLivre).orElse(null);
       if (demande != null && livre != null) {
           // Affectez le livre à la demande
           livre.setdemande(demande);

       }
   }
}

   }*/
    @Override
    public Livre ajouetLivreAffecterCatalogue(long idCatalogues, Livre livre) {
        Catalogues catalogues = cataloguesRepository.findById(idCatalogues)
                .orElseThrow(() -> new EntityNotFoundException("Catalogue with ID " + idCatalogues + " not found."));

        livre.setCatalogues(catalogues);
        return livreRepository.save(livre);
    }
}