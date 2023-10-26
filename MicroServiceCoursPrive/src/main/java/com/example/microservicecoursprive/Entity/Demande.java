package com.example.microservicecoursprive.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Demande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idDemande;
    String NomParent;
    String NomEleve ;
    int NumTel ;
    String Localisation ;
    String Disponibilite ;
    @Enumerated(EnumType.STRING)
    Niveau niveau;
    @Enumerated(EnumType.STRING)
    Matiere matiere;
    Boolean demandeLivre = false ;


}
