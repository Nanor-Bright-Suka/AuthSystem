package com.backend.authsystem.authentication.seeder;

import com.backend.authsystem.authentication.entity.PermissionEntity;
import com.backend.authsystem.authentication.entity.RoleEntity;
import com.backend.authsystem.authentication.enums.PermissionEnum;
import com.backend.authsystem.authentication.enums.RoleEnum;
import com.backend.authsystem.authentication.repository.PermissionRepository;
import com.backend.authsystem.authentication.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;


@Component
@RequiredArgsConstructor
@Order(value = 2)
public class RoleSeeder implements ApplicationRunner {


    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(ApplicationArguments args) {

        // USER
        RoleEntity normalUser = roleRepository.findByRoleName(RoleEnum.ROLE_USER)
                .orElseGet(() -> roleRepository.save(
                RoleEntity.builder()
                        .roleId(UUID.randomUUID())
                        .roleName(RoleEnum.ROLE_USER)
                        .createdAt(Instant.now())
                        .build()
        ));

        add(normalUser,
                PermissionEnum.PROFILE_VIEW,
               PermissionEnum.PROFILE_UPDATE,
                PermissionEnum.ACCOUNT_VIEW,
                PermissionEnum.PASSWORD_CHANGE

        );
                // STUDENT
        RoleEntity student = roleRepository.findByRoleName(RoleEnum.ROLE_STUDENT)
                .orElseGet(() -> roleRepository.save(
                RoleEntity.builder()
                        .roleId(UUID.randomUUID())
                        .roleName(RoleEnum.ROLE_STUDENT)
                        .createdAt(Instant.now())
                        .build()
        ));

        add(student,
                PermissionEnum.COURSE_VIEW,
               PermissionEnum.ASSIGNMENT_SUBMIT,
                PermissionEnum.COURSE_ENROLL,
                PermissionEnum.ASSIGNMENT_VIEW,
                PermissionEnum.COURSE_MATERIAL_VIEW,
                PermissionEnum.CLASS_FEEDBACK_SUBMIT,
                PermissionEnum.GENERAL_ANNOUNCEMENT_VIEW
        );


        // COURSE REP
        RoleEntity courseRep = roleRepository.findByRoleName(RoleEnum.ROLE_COURSE_REP)
                .orElseGet(() -> roleRepository.save(
                        RoleEntity.builder()
                                .roleId(UUID.randomUUID())
                                .roleName(RoleEnum.ROLE_COURSE_REP)
                                .createdAt(Instant.now())
                                .build()
                ));

        add(courseRep,
              PermissionEnum.CLASS_ANNOUNCEMENT_VIEW,
                PermissionEnum.CLASS_FEEDBACK_COLLECT,
                PermissionEnum.CLASS_ANNOUNCEMENT_RELAY,
                PermissionEnum.CLASS_FEEDBACK_FORWARD
        );

                // LECTURER
        RoleEntity lecturer = roleRepository.findByRoleName(RoleEnum.ROLE_LECTURER)
                .orElseGet(() -> roleRepository.save(
                        RoleEntity.builder()
                                .roleId(UUID.randomUUID())
                                .roleName(RoleEnum.ROLE_LECTURER)
                                .createdAt(Instant.now())
                                .build()
                ));

        add(lecturer,
                PermissionEnum.ASSIGNMENT_GRADE,
                PermissionEnum.ASSIGNMENT_CREATE,
                PermissionEnum.COURSE_CREATE,
                PermissionEnum.COURSE_DELETE,
                PermissionEnum. COURSE_UPDATE,
                PermissionEnum.COURSE_PUBLISH,
                PermissionEnum.COURSE_REP_ASSIGN,
                PermissionEnum.STUDENT_VIEW,
                PermissionEnum.COURSE_MATERIAL_CREATE,
                PermissionEnum.COURSE_MATERIAL_UPDATE,
                PermissionEnum.COURSE_MATERIAL_DELETE,
                PermissionEnum.CLASS_FEEDBACK_VIEW,
                PermissionEnum.CLASS_ANNOUNCEMENT_CREATE

                );


                // ADMIN (separate)
        RoleEntity admin = roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(
                        RoleEntity.builder()
                                .roleId(UUID.randomUUID())
                                .roleName(RoleEnum.ROLE_ADMIN)
                                .createdAt(Instant.now())
                                .build()
                ));

        add(admin,
                PermissionEnum.ROLE_ASSIGN,
                PermissionEnum.USER_MANAGE
        );
    }

    private void add(RoleEntity role, PermissionEnum... perms) {
        for (PermissionEnum p : perms) {
            PermissionEntity perm = permissionRepository.findByPermissionName(p).orElseThrow(() ->
                    new IllegalStateException("Permission not found"));;
            role.addPermission(perm);
        }
        roleRepository.save(role);
    }

}