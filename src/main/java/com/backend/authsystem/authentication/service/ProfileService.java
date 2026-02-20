package com.backend.authsystem.authentication.service;

import com.backend.authsystem.authentication.dto.ProfileResponseDto;
import com.backend.authsystem.authentication.dto.ProfileUpdateDto;
import com.backend.authsystem.authentication.entity.ProfileEntity;
import com.backend.authsystem.authentication.entity.AccountEntity;
import com.backend.authsystem.authentication.repository.ProfileRepository;
import com.backend.authsystem.authentication.repository.AccountRepository;
import com.backend.authsystem.authentication.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProfileService {
    private final AccountRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final ProfileMapper profileMapper;

    public ProfileResponseDto getMyProfileService() {
        String email = authenticatedUserService.getCurrentUserEmail();
        System.out.println("Fetching profile for user: " + email);
        AccountEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));


        ProfileEntity profile = profileRepository.findByUser(user)
                .orElseGet(() -> {
                    ProfileEntity created = profileMapper.createDefaultViewProfile(user);
                    return profileRepository.save(created);
                });

       return profileMapper.toResponse(profile);
    }


    public void updateMyProfileService(ProfileUpdateDto dto) {

        String email = authenticatedUserService.getCurrentUserEmail();

        AccountEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        ProfileEntity profile = profileRepository.findByUser(user)
                .orElseGet(() -> profileMapper.createDefaultProfileUpdate(dto, user));

        profileMapper.updateDefaultProfile(profile, dto);

        profileRepository.save(profile);
    }







}
