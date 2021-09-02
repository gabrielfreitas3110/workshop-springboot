package com.example.workshop.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.workshop.entities.Category;
import com.example.workshop.entities.Product;
import com.example.workshop.repositories.ProductRepository;
import com.example.workshop.services.exceptions.DatabaseException;
import com.example.workshop.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<Product> findAll() {
		return repository.findAll();
	}

	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Product insert(Product obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Product update(Long id, Product obj, Set<Category> set) {
		try {
			Product entity = repository.getOne(id);
			updateData(entity, obj, set);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Product entity, Product obj, Set<Category> set) {
		if (obj.getName() != null)
			entity.setName(obj.getName());
		if (obj.getDescription() != null)
			entity.setDescription(obj.getDescription());
		if (obj.getPrice() != null)
			entity.setPrice(obj.getPrice());
		if (obj.getImgUrl() != null)
			entity.setImgUrl(obj.getImgUrl());
		if (!set.isEmpty())
			entity.updateCategories(set);
	}
}