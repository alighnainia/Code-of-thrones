package com.example.microservicecatalogues.Service;

import com.example.microservicecatalogues.Entity.Age;
import com.example.microservicecatalogues.Entity.Livre;
import com.example.microservicecatalogues.Entity.Type;
import com.example.microservicecatalogues.Entity.langue;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IServiceLivre {
    public Livre ajouterLivre (Livre livre);
    public void deleteLivre( long idLivre);
    public Livre updateLivre(Livre livre, long idLivre);
    public List<Livre> getLivre() ;
    public void affecterLivreCatalogue( Livre livre ,long idLivre ,  long idCatalogues) ;
    List<Livre> findByCatalogues_AgeAndCatalogues_LangueAndCatalogues_Type( Age age, langue langue,Type type);
    Livre getLivreBytitre(String titre);

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
    Livre ajouetLivreAffecterCatalogue(long idCatalogues, Livre livre);
    // public void Affecterlivredemande( Long idLivre ,   String NomEleve);
}
