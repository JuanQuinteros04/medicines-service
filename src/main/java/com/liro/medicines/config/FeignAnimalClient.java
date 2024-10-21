package com.liro.medicines.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "animals-service")
public interface FeignAnimalClient {

    @RequestMapping(method = RequestMethod.GET, value = "/animals/hasPermissions")
    ResponseEntity<Void> hasPermissions( @RequestParam("animalId") Long animalId,
                                         @RequestParam("needWritePermissions") Boolean needWritePermissions,
                                         @RequestParam("onlyOwner") Boolean onlyOwner,
                                         @RequestParam("onlyVet") Boolean onlyVet,
                                         @RequestHeader("clinicId") Long clinicId,
                                         @RequestHeader(name = "Authorization") String token);

}
