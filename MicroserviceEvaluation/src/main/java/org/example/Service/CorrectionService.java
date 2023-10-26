package org.example.Service;

import lombok.AllArgsConstructor;

import org.example.Entity.Correction;
import org.example.Entity.Evaluation;
import org.example.Repository.CorrectionRepo;
import org.example.Repository.EvaluationRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CorrectionService implements CorrectionServiceI{

    public final CorrectionRepo correctionRepo;
    public final EvaluationRepo evaluationRepo;
    @Override
    public Correction createCorrection(Correction correction) {

        return correctionRepo.save(correction);
    }

    @Override
    public Correction modifyCorrection(Correction modifiedCorrection, Long idC) {
        Optional<Correction> originalCorrectionOptional = correctionRepo.findById(idC);

        if (originalCorrectionOptional.isPresent()) {
            Correction originalEvaluation = originalCorrectionOptional.get();

            {
                originalEvaluation.setCommentaire(modifiedCorrection.getCommentaire());
                originalEvaluation.setRemarquesSupplementaires(modifiedCorrection.getRemarquesSupplementaires());

                return correctionRepo.save(originalEvaluation);
            }
        }return null;
    }
    @Override
    public Correction retrieveCorrection(Long idC) {
        return correctionRepo.findById(idC).orElseThrow(() -> new NullPointerException("correction not found"));
    }
    @Override
    public void deleteCorrection(Long idC) {
        Correction e = correctionRepo.findById(idC).get();
        correctionRepo.deleteById(idC);}
    @Override
    public Correction createCorrectionRelatedToEvaluation(long idE, Correction correction) {
        Evaluation evaluation = evaluationRepo.findById(idE)
                .orElseThrow(() -> new EntityNotFoundException("Evaluation with ID " + idE + " not found."));

        correction.setEvaluation(evaluation);
        return correctionRepo.save(correction);
    }
    @Override
    public List<Correction> getCorrectionsByEvaluationId(Long idE) {
        return correctionRepo.findAllByEvaluation_IdE(idE);
    }


}









