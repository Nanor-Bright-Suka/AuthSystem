package com.backend.authsystem.authentication.service;

import com.backend.authsystem.authentication.dto.ProfileResponseDto;
import com.backend.authsystem.authentication.dto.ProfileUpdateDto;
import com.backend.authsystem.authentication.entity.ProfileEntity;
import com.backend.authsystem.authentication.entity.AccountEntity;
import com.backend.authsystem.authentication.exception.UserNotFoundException;
import com.backend.authsystem.authentication.repository.ProfileRepository;
import com.backend.authsystem.authentication.repository.AccountRepository;
import com.backend.authsystem.authentication.mapper.ProfileMapper;
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
        AccountEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


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
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ProfileEntity profile = profileRepository.findByUser(user)
                .orElseGet(() -> profileMapper.createDefaultProfileUpdate(user));

        profileMapper.updateDefaultProfile(profile, dto);

        profileRepository.save(profile);
    }







}
