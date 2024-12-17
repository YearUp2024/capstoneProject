package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.models.Profile;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController{
    private ProfileDao profileDao;

    @Autowired
    public ProfileController(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Profile getProfileById(@PathVariable int id){
        if(profileDao.getByUserId(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not Found");
        }
        return profileDao.getByUserId(id);
    }
}
