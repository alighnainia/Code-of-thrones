package com.project.pppreclamationmicroservice.usermcroservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="AuthentificationMicroService", url = "http://authentification:8093/user")
public interface UserDtoClient {

    @GetMapping("/listUser")
    List<UserDto> listUsers();
    @GetMapping("/retrieveUser/{username}")
    UserDto retrieveUserInfo(@PathVariable("username") String username);
}
