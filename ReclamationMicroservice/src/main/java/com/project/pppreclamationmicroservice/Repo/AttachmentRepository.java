package com.project.pppreclamationmicroservice.Repo;


import com.project.pppreclamationmicroservice.Entity.Attachment;
import com.project.pppreclamationmicroservice.Entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
    List<Attachment> findByClaim(Claim claim);

}
