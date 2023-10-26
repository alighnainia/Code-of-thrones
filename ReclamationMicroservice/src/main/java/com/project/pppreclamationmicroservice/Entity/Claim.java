package com.project.pppreclamationmicroservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Claim implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    Date dateDiff;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateRes;
    //@NotEmpty(message = "Title required")
    String title;
    //@NotEmpty(message = "Description required")
    String description;
    String cause;
    @Enumerated(EnumType.STRING)
    Status status;
    @Enumerated(EnumType.STRING)
    TypeReclamation typeReclamation;
    @Size(min = 3, max = 20, message = "Min length 3 and Max length 20")
    String username;
    //@Email
    String email;
    @JsonIgnore
    @OneToMany(cascade= {CascadeType.REMOVE}, mappedBy = "claim")
    Set<Attachment> attachments=new HashSet<>();




}