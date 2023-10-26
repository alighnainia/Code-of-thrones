package org.example.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class Evaluation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idE;
    private String titre;
    private String contenu;
    private String auteur;
    @Temporal(TemporalType.DATE)
    private Date date;

    @JsonIgnore
    @OneToOne(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private Correction correction;


}
