package com.drinkme.admin.user;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.drinkme.common.entity.User;



@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@MockBean
	private UserRepository repo;
	
	@InjectMocks
	private UserService service;
	
    @Test
    public void testIsEmailUniqueNewUserNoExistingEmail() {
        
        Integer id = null;
        String email = "newuser@example.com";
        
        Mockito.when(repo.getUserByEmail(email)).thenReturn(null);

        assertThat(service.isEmailUnique(id, email)).isTrue();
    }
	
    
    @Test
    public void testIsEmailUniqueNewUserExistingEmail() {
        
        Integer id = null;
        String existingEmail = "existinguser@example.com";
        User existingUser = new User(existingEmail, "password123", "Existing", "User");
        Mockito.when(repo.getUserByEmail(existingEmail)).thenReturn(existingUser);

        
        boolean isUnique = service.isEmailUnique(id, existingEmail);

        
        assertThat(isUnique).isFalse();
    }
    
    
    @Test
    public void testIsEmailUniqueExistingUserSameEmail() {
        Integer userId = 1;
        String userEmail = "existinguser@example.com";
        User existingUser = new User(userEmail,"password123", "Existing", "User");
        
        Mockito.when(repo.getUserByEmail(userEmail)).thenReturn(existingUser);

        boolean isUnique = service.isEmailUnique(userId, userEmail);

        assertThat(isUnique).isFalse();
    }
    
    @Test
    public void testIsEmailUniqueExistingUserDifferentEmail() {
        // Given
        Integer userId = 1;
        String existingEmail = "existinguser@example.com";
        String differentEmail = "differentemail@example.com";
        User existingUser = new User(existingEmail, "password123", "Existing", "User");
        Mockito.when(repo.getUserByEmail(existingEmail)).thenReturn(existingUser);


        boolean isUnique = service.isEmailUnique(userId, differentEmail);

        assertThat(isUnique).isTrue();
    }
    
    @Test
    public void testDeleteExistingUser() {
        Integer userId = 1;
        Mockito.when(repo.countById(userId)).thenReturn(1L);
        assertDoesNotThrow(() -> service.delete(userId));
        Mockito.verify(repo, times(1)).deleteById(userId);
    }
    
    @Test
    public void testDeleteNonExistingUser() {
        Integer userId = 1;
        Mockito.when(repo.countById(userId)).thenReturn(null);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> service.delete(userId));
        assertEquals("Impossibile trovare utenti con ID: " + userId, exception.getMessage());

        // Verify that deleteById() is not called
        Mockito.verify(repo, never()).deleteById(userId);
    }
    
    @Test
    public void testGetExistingUser() throws UserNotFoundException {
        // Given
        Integer userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        Mockito.when(repo.findById(userId)).thenReturn(Optional.of(existingUser));
        User result = service.get(userId);
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }
    
    
    @Test
    public void testGetNonExistingUser() {
        Integer userId = 1;
        Mockito.when(repo.findById(userId)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.get(userId));
    }
    
    @Test
    public void testGetUserThrowsException() {
        // Given
        Integer userId = 1;

        Mockito.when(repo.findById(userId)).thenThrow(NoSuchElementException.class);

        // When/Then
        assertThrows(UserNotFoundException.class, () -> service.get(userId));
    }
    

    @Test
    public void testUpdateUserEnabledStatusEnable() {
        Integer userId = 1;
        boolean enabled = true;
        service.updateUserEnabledStatus(userId, enabled);
        Mockito.verify(repo, times(1)).updateEnabledStatus(userId, enabled);
    }
    
    @Test
    public void testUpdateUserEnabledStatusDisable() {
        Integer userId = 1;
        boolean enabled = false;
        service.updateUserEnabledStatus(userId, enabled);
        Mockito.verify(repo, times(1)).updateEnabledStatus(userId, enabled);
    }
    
    
    
}



