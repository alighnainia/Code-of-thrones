package com.example.microservicecatalogues.Service;

import com.example.microservicecatalogues.Entity.Catalogues;

import java.util.List;

public interface IServiceCatalogues {
    public Catalogues ajouterCatalogue(Catalogues catalogues);
    public void deleteCatalogues( long idCatalogues) ;
    public Catalogues updateCatalogue(Catalogues catalogues, long idCatalogues) ;
    public List<Catalogues> getCatalogues() ;
}
