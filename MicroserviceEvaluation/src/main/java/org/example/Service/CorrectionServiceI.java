package org.example.Service;

import org.example.Entity.Correction;

import java.util.List;
import java.util.Optional;

public interface CorrectionServiceI {
    Correction createCorrection(Correction correction);

    Correction modifyCorrection(Correction modifiedCorrection, Long idC);

    Correction retrieveCorrection(Long idC);

    void deleteCorrection(Long idC);



    Correction createCorrectionRelatedToEvaluation(long IdE, Correction correction);


    List<Correction> getCorrectionsByEvaluationId(Long idE);
}
