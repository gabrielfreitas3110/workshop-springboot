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

import com.example.workshop.entities.Product;
import com.example.workshop.services.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;
	
	@RequestMapping(value = "/products")
	public String findAll(Model model) {
		 List<Product> list = service.findAll();
		model.addAttribute("products",list);
		return "products";
	}
	
	@RequestMapping(value = "/products/add")
	public String getNewProduct() {
		return "newProduct";
	}
	
	@RequestMapping(value = "/products/add", method = RequestMethod.POST)
	public String saveUser(@Valid Product product, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("message", "Required fields are missing.");
			return "redirect:/products/add";
		}
		service.insert(product);
		return "redirect:/products";
	}
	
	@GetMapping(value = "/products/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		service.delete(id);
		return "redirect:/products";
	}
	
	@RequestMapping(value = "/products/edit/{id}")
	public String update() {
		return "updateProduct";
	}
	
	@RequestMapping(value = "products/edit/{id}", method = RequestMethod.POST)
	public String updateUser(@Valid Product product, @PathVariable("id") Long id, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("message", "Required fields are missing.");
			return "products/updateProducts";
		}
		service.update(id, product, product.getCategories());
		return "products";
	}
}