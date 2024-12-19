package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;

import java.util.List;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController{
    private ProfileDao profileDao;
    private UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Profile> create(@RequestBody Profile profile){
        if(profile.getUserId() <= 0){
            return ResponseEntity.badRequest().build();
        }

        Profile create = profileDao.create(profile);
        return ResponseEntity.ok(create);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Profile> getProfileById(@PathVariable int id){
        Profile profile = profileDao.getByUserId(id);

        if(profile == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    // add the appropriate annotation for a get action
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<Profile> getAll(){
        return profileDao.getAllProfile();
    }

    @PutMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile){
        Profile update = profileDao.updateProfile(profile);

        if(update == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(update);
    }
}
