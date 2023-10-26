package com.project.pppreclamationmicroservice.services;


import com.project.pppreclamationmicroservice.DTO.ClaimDto;
import com.project.pppreclamationmicroservice.Entity.Claim;
import com.project.pppreclamationmicroservice.Entity.Status;
import com.project.pppreclamationmicroservice.Entity.TypeReclamation;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

public interface IServiceClaim {
     Claim addClaim (Claim claim);
     List<Claim> findAllClaims();
     Claim findClaimById(Long id);
     //Claim changeStatus(Long id,Claim claimRequest);
     Claim changeStatus(Claim claim);

    Claim changeAnyStatus(Long id, ClaimDto claimDto);

    void cancelClaim(Long id);
     void removeClaim(Long id);
     Integer nbClaimsResolu(Date dateDiff, Date dateRes);
     Claim resolveClaim(Claim claim);

    Claim resolveStatusClaim(Long id);

    //Claim returnClaim(Long id,Claim claim);
     Claim returnClaim(Claim claim);

     Claim returnStatusClaim(Long id);

     //Claim archiveClaim(Long id);
     Claim archiveClaim(Claim claim);

    Claim archiveStatusClaim(Long id);

    Claim editClaim(Claim claim);
     List<Claim> findReclamationByStatus();
     List<Claim> findByDateDiff(Date startDate, Date endDate);
     List<Claim> findByDateRes(Date startDate, Date endDate);

     List<Claim> findClaimsByTypeReclamation(TypeReclamation type);

     List<Claim> search(String keywords);
     String Noticate();
     List<Claim> findByUsername(String username);

    @Scheduled(cron = "0 0 0 * * *")
    void deleteClaimByStatusResolu();

    long getDaysBetween(Long id);
     List<Claim> findClaimsByStatus(Status status);
     List<Claim> findHistory();









}