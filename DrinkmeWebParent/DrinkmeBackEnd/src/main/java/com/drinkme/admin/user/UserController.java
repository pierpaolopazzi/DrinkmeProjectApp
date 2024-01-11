package com.drinkme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drinkme.common.entity.Role;
import com.drinkme.common.entity.User;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/utenti")
	public String listFirstPage(Model model) {
		return listByPage(1, model, null);
	}
	
	@GetMapping("/utenti/page/{pageNum}")
	public String listByPage(
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("keyword") String keyword) {
		Page<User> page = service.listByPage(pageNum, keyword);
		List<User> listUsers = page.getContent();
		
		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE;
		long endCount = startCount + UserService.USERS_PER_PAGE -1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount); 
		model.addAttribute("totalItems",  page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("keyword", keyword); 
		
		return "utenti";
	}
	
	@GetMapping("/utenti/nuovo_utente")
	public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();
		User utente = new User();
		utente.setEnabled(true);
		
		model.addAttribute("utente", utente);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Crea nuovo utente");
		
		return "form_utente";
	}
	
	@PostMapping("/utenti/salva")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		System.out.println(user);
		service.save(user);
		
		redirectAttributes.addFlashAttribute("message", "L'utente e' stato salvato con successo!");
		return "redirect:/utenti";
	}
	
	@GetMapping("/utenti/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try{
			User utente = service.get(id);
			List<Role> listRoles = service.listRoles();
			
			model.addAttribute("utente", utente);
			model.addAttribute("pageTitle", "Modifica utente (ID: "+id+")");
			model.addAttribute("listRoles", listRoles);
			
			return "form_utente";
		} catch(UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/utenti";
		}
	}
	
	@GetMapping("/utenti/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try{
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "L'utente ID: "+id+" é stato eliminato correttamente"); 
		} catch(UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/utenti";
	}
	
	@GetMapping("/utenti/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "abilitato" : "disabilitato";
		String message = "L'utente " + id + " é stato " + status;
		redirectAttributes.addFlashAttribute("message", message);
	
		return "redirect:/utenti";
	}
}
