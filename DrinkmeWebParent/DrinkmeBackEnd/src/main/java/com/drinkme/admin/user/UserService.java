package com.drinkme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.drinkme.common.entity.Role;
import com.drinkme.common.entity.User;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserService {
	public static final int USERS_PER_PAGE = 5;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> listAll() {
		return (List<User>) userRepo.findAll();
	}
	
	public Page<User> listByPage(int pageNum, String keyword) {
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
		
		if(keyword != null) {
			return userRepo.findAll(keyword, pageable);
		}
		
		return userRepo.findAll(pageable);
	}
	
	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll(); 
	}
	
	public void save(User user) {
		
		// vero se esiste utente con id
		boolean isUpdatingUser = (user.getId() !=  null);
		
		// verifico che sia una modifica 
		// piuttosto che un nuovo utente
		if(isUpdatingUser) {
			
			// recupero utente esistente
			User existingUser = userRepo.findById(user.getId()).get();
			
			// codifico e aggiorno la password
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
		}else {
			encodePassword(user);
		}
		userRepo.save(user);
	}

	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	// Controllo che indirizzo email che voglio inserire sia univoco rispetto a quelli già presenti 
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		
		// Nessun utente con l'indirizzo specificato
		if(userByEmail == null) return true;
		
		
		boolean isCreatingNew = (id == null);
		
		// verifico se si sta creando un nuovo utente
		if(isCreatingNew) {
			if(userByEmail != null) return false; // esiste già utente con lo stesso indirizzo email
		} else {
			if(userByEmail.getId() != id) { 
				return false;			// indirizzo email appartiene ad un altro utente
			}
		}
		
		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Impossibile trovare utente con id: " + id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Impossibile trovare utenti con ID: " + id);
		}
		
		userRepo.deleteById(id);
	}
	
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}

}
