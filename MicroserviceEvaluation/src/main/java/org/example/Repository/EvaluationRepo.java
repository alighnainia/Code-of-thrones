package org.example.Repository;

import org.example.Entity.Correction;
import org.example.Entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface EvaluationRepo extends JpaRepository<Evaluation,Long> {
    Optional<Evaluation> findById(Long idE);
    List<Evaluation> findByOrderByDate();
    List<Evaluation> findByOrderByAuteur();
    List<Evaluation> findByOrderByTitre();
    List<Evaluation> findByOrderByContenu();
    Optional<Correction> getCorrectionByIdE(Long idE);

}
