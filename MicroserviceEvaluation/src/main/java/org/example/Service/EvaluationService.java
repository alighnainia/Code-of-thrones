package org.example.Service;

import lombok.AllArgsConstructor;
import org.example.Entity.Correction;
import org.example.Entity.Evaluation;
import org.example.Repository.CorrectionRepo;
import org.example.Repository.EvaluationRepo;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service


public class EvaluationService implements EvaluationServiceI {

    public final EvaluationRepo evaluationRepo;
    public final CorrectionRepo correctionRepo;

    @Override
    public Evaluation createEvaluation(Evaluation evaluation) {
        return evaluationRepo.save(evaluation);
    }

    @Override
    public Evaluation modifyEvaluation(Evaluation modifiedEvaluation, Long idE) {
        Optional<Evaluation> originalEvaluationOptional = evaluationRepo.findById(idE);

        if (originalEvaluationOptional.isPresent()) {
            Evaluation originalEvaluation = originalEvaluationOptional.get();

            {
                originalEvaluation.setTitre(modifiedEvaluation.getTitre());
                originalEvaluation.setContenu(modifiedEvaluation.getContenu());

                return evaluationRepo.save(originalEvaluation);
            }
        }
        return null;
    }

    @Override
    public Evaluation retrieveEvaluation(Long idE) {
        return evaluationRepo.findById(idE).orElseThrow(() -> new NullPointerException("evaluation not found"));
    }

    @Override
    public void deleteEvaluation(Long idE) {
        Evaluation e = evaluationRepo.findById(idE).get();
        evaluationRepo.deleteById(idE);

    }

    @Override
    public List<Evaluation> getEvaluationsByFilter(String filterType) {
        List<Evaluation> evaluations;

        switch (filterType) {
            case "date":
                evaluations = evaluationRepo.findByOrderByDate();
                break;
            case "auteur":
                evaluations = evaluationRepo.findByOrderByAuteur();
                break;
            case "titre":
                evaluations = evaluationRepo.findByOrderByTitre();
                break;
            case "contenu":
                evaluations = evaluationRepo.findByOrderByContenu();
                break;
            default:
                evaluations = evaluationRepo.findAll();
                break;
        }

        return evaluations;
    }

    public void exportToCSV(Long idE, PrintWriter writer) {
        Evaluation evaluation = evaluationRepo.findById(idE).orElse(null);

        if (evaluation != null) {
            writer.append("ID, Titre, Contenu, Auteur, Date\n");
            writer.append(evaluation.getIdE() + ",");
            writer.append(evaluation.getTitre() + ",");
            writer.append(evaluation.getContenu() + ",");
            writer.append(evaluation.getAuteur() + ",");
            writer.append(evaluation.getDate().toString() + "\n");
        }


    }


}








