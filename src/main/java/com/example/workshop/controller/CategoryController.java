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

import com.example.workshop.entities.Category;
import com.example.workshop.services.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@RequestMapping(value = "/categories")
	public String findAll(Model model) {
		 List<Category> list = service.findAll();
		model.addAttribute("categories",list);
		return "categories";
	}
	
	@RequestMapping(value = "/categories/add")
	public String getNewCategory() {
		return "newCategory";
	}
	
	@RequestMapping(value = "/categories/add", method = RequestMethod.POST)
	public String saveUser(@Valid Category category, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("message", "Required fields are missing.");
			return "redirect:/categories/add";
		}
		service.insert(category);
		return "redirect:/categories";
	}
	
	@GetMapping(value = "/categories/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		service.delete(id);
		return "redirect:/categories";
	}
	
	@RequestMapping(value = "/categories/edit/{id}")
	public String update() {
		return "updateCategory";
	}
	
	@RequestMapping(value = "categories/edit/{id}", method = RequestMethod.POST)
	public String updateCategory(@Valid Category category, @PathVariable("id") Long id, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("message", "Required fields are missing.");
			return "categories/updateCategory";
		}
		service.update(id, category);
		return "users";
	}
}