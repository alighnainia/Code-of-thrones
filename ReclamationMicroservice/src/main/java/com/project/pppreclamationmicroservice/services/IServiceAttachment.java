package com.project.pppreclamationmicroservice.services;


import com.project.pppreclamationmicroservice.Entity.Attachment;
import com.project.pppreclamationmicroservice.Responses.MessageResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IServiceAttachment {


    List<Attachment> findByClaim(Long id);

    MessageResponse uploadFile(List<MultipartFile> files, Long id);

    Resource download(String filename) ;


}
