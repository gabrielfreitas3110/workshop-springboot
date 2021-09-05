package com.example.workshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.workshop.entities.User;
import com.example.workshop.services.UserService;

import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/users")
	public String findAll(Model model) {
		 List<User> list = service.findAll();
		//list.forEach(u -> u.setPassword(null));
		list.forEach(u -> u.setPassword("*".repeat(u.getPassword().length())));
		model.addAttribute("users",list);
		return "users";
	}
	
	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		service.delete(id);
		return "redirect:/users" ;
	}
	
	@RequestMapping(value = "/users/add")
	public String insert() {
		return "newUser";
	}
	
	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
	public String savePost(@Valid User user, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("message", "Required fields are missing.");
			return "redirect:/users/add";
		}
		service.insert(user);
		return "redirect:/users";
	}
}