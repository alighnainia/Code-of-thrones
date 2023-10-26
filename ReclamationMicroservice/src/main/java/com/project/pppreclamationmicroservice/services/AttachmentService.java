package com.project.pppreclamationmicroservice.services;


import com.project.pppreclamationmicroservice.Entity.Attachment;
import com.project.pppreclamationmicroservice.Entity.Claim;
import com.project.pppreclamationmicroservice.Repo.AttachmentRepository;
import com.project.pppreclamationmicroservice.Repo.ClaimRepository;
import com.project.pppreclamationmicroservice.Responses.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@AllArgsConstructor
public class AttachmentService implements IServiceAttachment{
    private AttachmentRepository attachmentRepository;
    private ClaimRepository claimRepository;

    @Override
    public List<Attachment> findByClaim(Long id) {
        Claim claim = claimRepository.findById(id).orElse(null);
        claim.setId(id);
        return attachmentRepository.findByClaim(claim);
    }
    private final Path root = Paths.get("uploads");
    @Override
    public MessageResponse uploadFile(List<MultipartFile> files, Long id){
        Claim claim = claimRepository.findById(id).orElse(null);
        claim.setId(id);
        try {
            for (MultipartFile file: files) {
                Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                Attachment attachment = new Attachment();
                attachment.setClaim(claim);
                attachment.setPath(file.getOriginalFilename());
                attachmentRepository.save(attachment);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return  new MessageResponse(true, "Success", "Operation  effectuated");

    }

    @Override
    public Resource download(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return resource;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());

        }
    }


}