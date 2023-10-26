package com.example.microservicecatalogues.feign;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Demande  {

    Long idDemande;
    String NomParent;
    String NomEleve ;
    int NumTel ;
    String Localisation ;
    String Disponibilite ;
    String niveau ;
    String matiere;
    Boolean demandeLivre  ;



}

