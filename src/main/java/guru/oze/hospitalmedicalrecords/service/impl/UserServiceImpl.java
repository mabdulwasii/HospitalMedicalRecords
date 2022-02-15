package guru.oze.hospitalmedicalrecords.service.impl;

import guru.oze.hospitalmedicalrecords.entity.Authority;
import guru.oze.hospitalmedicalrecords.entity.StaffDto;
import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.entity.enumeration.AuthorityType;
import guru.oze.hospitalmedicalrecords.exception.GenericException;
import guru.oze.hospitalmedicalrecords.exception.InvalidUserNameException;
import guru.oze.hospitalmedicalrecords.repository.AuthorityRepository;
import guru.oze.hospitalmedicalrecords.repository.UserRepository;
import guru.oze.hospitalmedicalrecords.service.UserService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;
import guru.oze.hospitalmedicalrecords.utils.DtoTransformer;
import guru.oze.hospitalmedicalrecords.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final SecurityUtil securityUtil;

    @Override
    public ApiResponse registerStaff(StaffInfo staffInfo) {
        if (staffInfo.getPassword().equalsIgnoreCase(staffInfo.getConfirmPassword())) {
            String encryptedPassword = passwordEncoder.encode(staffInfo.getPassword());
            Set<Authority> authorities = new HashSet<>();
            authorityRepository.findByName(AuthorityType.ROLE_USER).ifPresent(authorities::add);

            User user = DtoTransformer.transformStaffInfoToUserEntity(staffInfo, encryptedPassword, authorities);
            User createdUser = userRepository.save(user);
            log.info("registered staff user {}", createdUser);

            StaffDto staffDto = DtoTransformer.transformUserEntityToStaffDto(createdUser);
            log.info("transformed staffDto {}", staffDto);
            return DtoTransformer.buildApiResponse("Staff registered successfully", staffDto);
        }

        throw new GenericException("Password mismatch");
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username);
    }

    @Override
    public ApiResponse updateStaff(HttpServletRequest request, StaffDto staffDto) {
        securityUtil.ensureApiKeyIsValid(request);
        Optional<User> userOptional = userRepository.findById(staffDto.getId());
        if (userOptional.isEmpty()) {
            throw new InvalidUserNameException("Invalid username");
        }
        userOptional
                .map(user -> {
                    user.setUsername(staffDto.getUsername().toLowerCase());
                    user.setFirstName(staffDto.getFirstName());
                    user.setLastName(staffDto.getLastName());
                    user.setPassword(user.getPassword());
                    user.setActivated(user.isActivated());
                    user.setAuthorities(user.getAuthorities());
					log.debug("Changed Information for User: {}", user);
					return DtoTransformer.buildApiResponse("Staff updated successfully", user);
				});

		StaffDto updatedStaff = DtoTransformer.transformUserEntityToStaffDto(userOptional.get());

		return DtoTransformer.buildApiResponse("Staff updated successfully", updatedStaff);
    }

    @Override
    public ApiResponse getAllStaffs(HttpServletRequest request) {
        securityUtil.ensureApiKeyIsValid(request);
        List<User> staffList = userRepository.findAll();
        log.info("Retrieved staff list {}", staffList);

        List<StaffDto> staffDtoList = DtoTransformer.transformUserEntityListToStaffDtoList(staffList);
        log.info("Retrieved staff dto list {}", staffDtoList);

        return DtoTransformer.buildApiResponse(staffDtoList);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
