package com.example.microservicecatalogues.Service;

import com.example.microservicecatalogues.Entity.Catalogues;
import com.example.microservicecatalogues.Repository.CataloguesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCatalogues implements IServiceCatalogues{
private CataloguesRepository cataloguesRepository;
    @Override
    public Catalogues ajouterCatalogue(Catalogues catalogues){
    return cataloguesRepository.save(catalogues);
}

    @Override
    public void deleteCatalogues( long idCatalogues) {
        Catalogues catalogues = cataloguesRepository.getReferenceById(idCatalogues);
        cataloguesRepository.delete(catalogues);
    }

    @Override
    public Catalogues updateCatalogue(Catalogues catalogues, long idCatalogues){
            Catalogues catalogues1 = cataloguesRepository.findById(idCatalogues).orElse(null);
            cataloguesRepository.save(catalogues);
            return catalogues1 ;
    }

    @Override
    public List<Catalogues> getCatalogues() {
        return cataloguesRepository.findAll();
}
}
