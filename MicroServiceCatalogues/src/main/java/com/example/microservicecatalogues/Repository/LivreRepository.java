package com.example.microservicecatalogues.Repository;

import com.example.microservicecatalogues.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LivreRepository extends JpaRepository <Livre,Long> {
    List<Livre> findByCatalogues_AgeAndCatalogues_LangueAndCatalogues_Type( Age age, langue langue,Type type);
Livre getLivreBytitre(String titre);
}
