package org.yearup.data;

import org.yearup.models.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileDao
{
    Optional <Profile> create(Profile profile);
    Optional <Profile> getByUserId(int userId);
    List<Profile> getAllProfile();
    Optional <Profile> updateProfile(Profile profile);
}
