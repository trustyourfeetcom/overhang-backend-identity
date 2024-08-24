package com.trustyourfeet.overhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trustyourfeet.overhang.entity.Profile;
import com.trustyourfeet.overhang.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfile(Long id, String username, String email) {
        Profile profile = new Profile();
        profile.setId(id);
        profile.setUsername(username);
        profile.setEmail(email);
        return profileRepository.save(profile);
    }
}
