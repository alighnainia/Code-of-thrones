package com.project.pppreclamationmicroservice.services;


import com.project.pppreclamationmicroservice.DTO.ClaimDto;
import com.project.pppreclamationmicroservice.Entity.Claim;
import com.project.pppreclamationmicroservice.Entity.Status;
import com.project.pppreclamationmicroservice.Entity.TypeReclamation;
import com.project.pppreclamationmicroservice.Repo.ClaimRepository;
import freemarker.template.Configuration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class ClaimService implements IServiceClaim {

    private ClaimRepository claimRepository;
    private Configuration config;



    @Override
    public Claim addClaim(Claim claim) {
        claim.setDateDiff(new Date());
        claim.setStatus(Status.EN_COURS);
        Claim newClaim = claimRepository.save(claim);
        return newClaim;
    }

    @Override
    public List<Claim> findAllClaims() {
        return claimRepository.findAll();
    }

    @Override
    public Claim findClaimById(Long id) {
        return claimRepository.findById(id).orElse(null);
    }

    /*@Override
    public Claim changeStatus(Long id,Claim claimRequest) {

        Claim claim = claimRepository.findById(id).orElse(null);

        claim.setStatus(claimRequest.getStatus());
        return claimRepository.save(claim);
    }*/
    @Override
    public Claim changeStatus(Claim claim){
        return claimRepository.save(claim);

    }
    @Override
    public Claim changeAnyStatus(Long id, ClaimDto claimDto){
        Claim claim = claimRepository.findById(id).orElse(null);
        claim.setStatus(claimDto.getStatus());
        return claimRepository.save(claim);

    }


    @Override
    public void cancelClaim(Long id) {
        Claim claim = claimRepository.findById(id).orElse(null);
        if (claim.getStatus().equals(Status.EN_COURS)){
            claim.setStatus(Status.ANNULER);
        }
        claimRepository.save(claim);
    }

    @Override
    public void removeClaim(Long id) {
        Claim claim = claimRepository.findById(id).orElse(null);
        if (claim.getStatus().equals(Status.ANNULER)){
            claimRepository.deleteById(id);
        }
    }

    @Override
    public Integer nbClaimsResolu(Date dateDiff, Date dateRes) {
        return claimRepository.countByStatusAndDateDiffBetween(Status.RESOLU,dateDiff,dateRes);
    }
    /*@Override
    public Claim resolveClaim(Claim claim){
        claim.setDateRes(new Date());
        claim.setStatus(Status.RESOLU);
        Claim cl = claimRepository.save(claim);
        sendEmailResolved(cl);
        return cl;

    }*/
    @Override
    public Claim resolveClaim(Claim claim){
        claim.setDateRes(new Date());
        claim.setStatus(Status.RESOLU);
        Claim cl = claimRepository.save(claim);
       // sendEmailResolved(cl);
        return cl;
    }
    @Override
    public Claim resolveStatusClaim(Long id){
        Claim claim = claimRepository.findById(id).orElse(null);
        claim.setDateRes(new Date());
        claim.setStatus(Status.RESOLU);
        return claimRepository.save(claim);
    }
    @Override
    public Claim returnClaim(Claim claim){

        claim.setStatus(Status.RETOUR);
        return claimRepository.save(claim);
    }
    @Override
    public Claim returnStatusClaim(Long id){
        Claim claim = claimRepository.findById(id).orElse(null);
        claim.setStatus(Status.RETOUR);
        return claimRepository.save(claim);
    }
    /*@Override
    public Claim returnClaim(Long id,Claim claimRequest){
        Claim claim = claimRepository.findById(id).orElse(null);
        claim.setStatus(Status.RETOUR);
        claim.setCause(claimRequest.getCause());
        return claimRepository.save(claim);
    }*/
    /*@Override
    public Claim archiveClaim(Long id){
        Claim claim = claimRepository.findById(id).orElse(null);
        if (claim.getStatus()==Status.RESOLU){
            claim.setStatus(Status.ARCHIVER);
        }
        return claimRepository.save(claim);

    }*/
    @Override
    public Claim archiveClaim(Claim claim){
        claim.setStatus(Status.ARCHIVER);
        return claimRepository.save(claim);
    }
    @Override
    public Claim archiveStatusClaim(Long id){
        Claim claim = claimRepository.findById(id).orElse(null);
        claim.setStatus(Status.ARCHIVER);
        return claimRepository.save(claim);
    }
    @Override
    public Claim editClaim(Claim claim){
        claim.setStatus(Status.MODIFIER);
        return claimRepository.save(claim);
    }

    @Override
    public List<Claim> findReclamationByStatus() {

        return claimRepository.findByStatusIsNot(Status.ANNULER);
    }

    @Override
    public List<Claim> findByDateDiff(Date startDate, Date endDate) {
        return claimRepository.findClaimsByDateDiffBetween(startDate,endDate);
    }

    @Override
    public List<Claim> findByDateRes(Date startDate, Date endDate) {
        return claimRepository.findClaimsByDateResBetween(startDate,endDate);
    }


    @Override
    public List<Claim> findClaimsByTypeReclamation(TypeReclamation type) {
        return claimRepository.findClaimsByTypeReclamation(type);
    }


    @Override
    public List<Claim> search(String keywords) {
        return claimRepository.findByKeywords(keywords);

    }

    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public String Noticate() {
        String msg=" ";
        List<Claim> claims = claimRepository.findByStatusIsNot(Status.ANNULER);
        for (Claim claim : claims) {
            if (claim.getStatus().equals(Status.EN_COURS)) {
                msg = "!!! Claim RECEIVED! Time to verify";
            }

        }
        return msg;

    }


    @Override
    public List<Claim> findByUsername(String username) {

        return claimRepository.findClaimsByUsername(username);
    }


    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void deleteClaimByStatusResolu() {
        List<Claim> claims = claimRepository.findByStatusIsNot(Status.ANNULER);
        for (Claim c : claims) {
            claimRepository.deleteByStatus(Status.RESOLU);
        }
    }






    public long getDaysBetween(Long id) {
        Claim claim = claimRepository.findById(id).orElse(null);

        LocalDate now = LocalDate.now();
        Date datt= Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        long daysDiff = datt.getTime() - claim.getDateDiff().getTime() ;//difference between two dates
        long diffInDays = TimeUnit.DAYS.convert(daysDiff, TimeUnit.MILLISECONDS);//convert result in days

        return diffInDays;
    }

    @Override
    public List<Claim> findClaimsByStatus(Status status) {
        return claimRepository.findClaimsByStatus(status);
    }
    @Override
    public List<Claim> findHistory() {
        return claimRepository.findClaimsByStatus(Status.ARCHIVER);
    }



}