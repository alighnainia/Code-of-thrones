package org.example.Service;

import org.example.Entity.Correction;
import org.example.Entity.Evaluation;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public interface EvaluationServiceI {


    Evaluation createEvaluation(Evaluation evaluation);

    Evaluation modifyEvaluation(Evaluation modifiedEvaluation, Long titre);

    Evaluation retrieveEvaluation(Long idE);

    void deleteEvaluation(Long idE);

    List<Evaluation> getEvaluationsByFilter(String filterType);


    void exportToCSV(Long idE, PrintWriter writer);



}
