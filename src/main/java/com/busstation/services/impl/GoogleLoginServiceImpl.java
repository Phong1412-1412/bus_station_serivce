package com.busstation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busstation.entities.Account;
import com.busstation.entities.Role;
import com.busstation.entities.User;
import com.busstation.enums.AuthenticationProvider;
import com.busstation.enums.NameRoleEnum;
import com.busstation.repositories.AccountRepository;
import com.busstation.repositories.RoleRepository;
import com.busstation.repositories.UserRepository;
import com.busstation.services.GoogleLoginService;

@Service
public class GoogleLoginServiceImpl implements GoogleLoginService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void CreateNewUserloginWithGoogle(String email, String fullname) {
		Account account = new Account();
		account.setUsername(email);
		account.setCancellationCount(0);
		account.setPassword(" ");
		Role role = roleRepository.findByName(NameRoleEnum.ROLE_USER.toString());
		account.setRole(role);
		accountRepository.save(account);
		User user = new User();
		user.setAccount(account);
		user.setFullName(fullname);
		user.setEmail(email);
		user.setPhoneNumber("");
		user.setAddress("");
		user.setStatus(Boolean.TRUE);
		user.setAuthProvider(AuthenticationProvider.GOOGLE);
		userRepository.save(user);
	}

}
