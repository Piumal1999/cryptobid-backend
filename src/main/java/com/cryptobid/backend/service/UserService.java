package com.cryptobid.backend.service;

import com.cryptobid.backend.exceptions.ResourceNotFoundException;
import com.cryptobid.backend.model.User;
import com.cryptobid.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	private final static Logger log = LoggerFactory.getLogger(UserService.class);
	private final UserRepository userRepository;

	public UserService( UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Retrieves the {@link User} filtered from {@code id}
	 *
	 * @param id which is the id of the filtering {@link User}
	 * @return {@link User}
	 * @throws ResourceNotFoundException if the requesting {@link User} doesn't exist
	 */
	public User getUserById(int id) throws ResourceNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			String msg = "Error, Auction by id: " + id + " doesn't exist.";
			log.error(msg);
			throw new ResourceNotFoundException(msg);
		}
		return user.get();
	}

}
