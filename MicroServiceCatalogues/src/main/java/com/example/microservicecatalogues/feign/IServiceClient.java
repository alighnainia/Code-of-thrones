package com.example.microservicecatalogues.feign;
import com.example.microservicecatalogues.Entity.Livre;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "MicroServiceCoursPrive", url = "http://localhost:8990")
public interface IServiceClient {
    @GetMapping("/LesDemandes")
    public List<Demande> getDemande();

    @GetMapping("/DemandeParMatiereNiveau/{matiere}/{niveau}")
    public List<Demande> findDemandeByMatiereAndNiveau(@PathVariable String matiere, @PathVariable String niveau);

    @GetMapping("/FdemandeLivre")
    public List<Demande> getDemandesAvecDemandeLivre();
    @GetMapping("/livres/{idLivre}")
    Livre getLivreById(@PathVariable Long idLivre);
}