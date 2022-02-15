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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
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
			log.info("Created staff user {}", createdUser);

			StaffDto staffDto = DtoTransformer.transformUserEntityToStaffDto(createdUser);
			log.info("transformed staffDto {}", staffDto);
			return DtoTransformer.buildApiResponse("Staff created successfully", staffDto);
        }

		throw new GenericException("Password mismatch");
    }

    @Override
    public Optional<User> findByUserName(String username) {
		return userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username);
    }

	@Override
	public ApiResponse updateStaff(StaffDto staffDto) {
		securityUtil.ensureApiKeyIsValid();
		if (!userRepository.existsByUsername(staffDto.getUsername())) {
			throw new InvalidUserNameException("Invalid username");
		}
		User staffUser = DtoTransformer.transformStaffDtoToUserEntity(staffDto);

		User updatedStaff = userRepository.save(staffUser);
		log.info("Updated staff user {}", updatedStaff);
		return DtoTransformer.buildApiResponse("Staff updated successfully", updatedStaff);
	}

	@Override
	public ApiResponse getAllStaffs() {
		securityUtil.ensureApiKeyIsValid();
		List<User> staffList = userRepository.findAll();
		log.info("Retrieved staff list {}", staffList);

		List<StaffDto> staffDtoList = DtoTransformer.transformUserEntityListToStaffDtoList(staffList);
		log.info("Retrieved staff dto list {}", staffDtoList);

		return DtoTransformer.buildApiResponse("Staff updated successfully", staffDtoList);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
}
