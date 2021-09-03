package com.example.workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.workshop.entities.User;
import com.example.workshop.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/users")
	public String findAll(Model model) {
		List<User> list = service.findAll();
		list.forEach(u -> u.setPassword(null));
		model.addAttribute("users",list);
		return "users";
	}
}