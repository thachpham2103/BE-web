package com.example.be.web;

import com.example.be.web.base.AdminInfoProperties;
import com.example.be.web.constant.RoleConstant;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.model.Role;
import com.example.be.web.repository.RoleRepository;
import com.example.be.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
//@EnableConfigurationProperties({AdminInfoProperties.class, StorageProperties.class})

public class BeWebApplication {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		Environment env = SpringApplication.run(BeWebApplication.class, args).getEnvironment();
		String appName = env.getProperty("spring.application.name");
		if (appName != null) {
			appName = appName.toUpperCase();
		}
		String port = env.getProperty("server.port");
		log.info("-------------------------START " + appName
				+ " Application------------------------------");
		log.info("   Application         : " + appName);
		log.info("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
		log.info("-------------------------START SUCCESS " + appName
				+ " Application------------------------------");
	}

	@Bean
	CommandLineRunner init(AdminInfoProperties userInfo) {
		return args -> {
			//init role
			if (roleRepository.count() == 0) {
				roleRepository.save(new Role(null, RoleConstant.ADMIN, null));
				roleRepository.save(new Role(null, RoleConstant.USER, null));
				roleRepository.save(new Role(null, RoleConstant.LEADER, null));
			}
			//init admin
			if (userRepository.count() == 0) {
				User admin = User.builder().username(userInfo.getUsername())
						.password(passwordEncoder.encode(userInfo.getPassword()))
						.fullName(userInfo.getFullName())
						.role(roleRepository.findByRoleName(RoleConstant.ADMIN))
						.lastLogin(LocalDateTime.now())
						.email(userInfo.getEmail())
						.build();
				User user = User.builder().username("thachpn")
						.password(passwordEncoder.encode("210305"))
						.fullName("Pham Ngoc Thach")
						.role(roleRepository.findByRoleName(RoleConstant.USER))
						.lastLogin(LocalDateTime.now())
						.email("thachpham2103@gmail.com")
						.build();

				userRepository.save(user);
				userRepository.save(admin);
			}
		};
	}
}
