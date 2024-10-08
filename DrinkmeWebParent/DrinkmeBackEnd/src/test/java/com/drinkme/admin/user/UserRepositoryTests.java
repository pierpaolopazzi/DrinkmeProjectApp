package com.drinkme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.drinkme.common.entity.Role;
import com.drinkme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userPier = new User("stefanoricci@gmail.com", "admin01", "Stefano", "Ricci");
		userPier.addRole(roleAdmin);
		
		User savedUser = repo.save(userPier);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
/*	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userLuca = new User("lucasardo@gmail.com", "lucasardo", "Luca", "Sardo");
		Role role1 = new Role(1);
		Role role2 = new Role(3);
		
		userLuca.addRole(role1);
		userLuca.addRole(role2);
	
		User savedUser = repo.save(userLuca);
		System.out.println("Utente salvato --> " + savedUser);
		//assertThat(savedUser.getId()).isGreaterThan(0);
	}
*/
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userPier = repo.findById(1).get();
		System.out.println(userPier);
	}
	
	@Test
	public void testGetUserByIdNonExistent() {
		Integer nonExistentId = 100;
		Optional<User> userOptional = repo.findById(nonExistentId);	
		assertThat(userOptional).isNotPresent();
	}
	
    @Test
    public void testGetUserByIdThrowsException() {
        Integer nonExistentId = 100;
        assertThrows(NoSuchElementException.class, () -> {
            repo.findById(nonExistentId).get();
        });
    }
	
	@Test
	public void testUpdateUserDetails() {
		User userPier = repo.findById(1).get();
		userPier.setEnabled(true);
		userPier.setEmail("pierpaolopazzi23@gmail.com");
		
		repo.save(userPier);
	}
	/*
	@Test
	public void testUpdateUserRoles() {
		User userLuca = repo.findById(7).get();
		Role roleCameriere = new Role(2);
		Role roleAdmin = new Role(1);
		
		userLuca.getRoles().remove(roleCameriere);
		userLuca.addRole(roleAdmin);
		
		repo.save(userLuca);
	}
	*/
	@Test
	public void testDeleteUser() {
		Integer userId = 7;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "filippo.salvi@gmail.com";
		User user = repo.getUserByEmail(email);
		
		//assertThat(user).isNotNull();
		System.out.println(user);
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		//assertThat(countById).isNull();
		System.out.println(countById);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 10;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 12;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 1;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "Stefano";
		
		int pageNumber = 0;
		int pageSize = 20;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
	}
	
	@Test
	public void testSearchUsersNonExisting() {
		String keyword = "Batman";
		
		int pageNumber = 0;
		int pageSize = 20;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(0); 
	}
	
}











