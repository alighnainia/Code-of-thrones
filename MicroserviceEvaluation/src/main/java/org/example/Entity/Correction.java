package org.example.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class Correction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idC;
    private String commentaire;
    @Temporal(TemporalType.DATE)
    private Date dateCorrection;
    private String remarquesSupplementaires;


@JsonIgnore
    @OneToOne
    @JoinColumn(name = "idE")
    private Evaluation evaluation;
}
