package org.example.Repository;

import feign.Param;
import org.example.Entity.Correction;
import org.example.Entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface CorrectionRepo extends JpaRepository<Correction,Long> {
    Optional<Correction> findById(Long idE);

    List<Correction> findAllByEvaluation_IdE(Long idE);

}
