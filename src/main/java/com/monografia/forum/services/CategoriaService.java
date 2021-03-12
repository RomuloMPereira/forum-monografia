package com.monografia.forum.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monografia.forum.dto.CategoriaDto;
import com.monografia.forum.entities.Categoria;
import com.monografia.forum.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoriaDto> findAll(){
		List<Categoria> list = repository.findAll();
		List<CategoriaDto> listDto = new ArrayList<CategoriaDto>();	
		for (Categoria entity : list) {
			listDto.add(new CategoriaDto(entity, entity.getTopicos(), entity.getSubcategorias()));
		}
		return listDto;
	}
	
	@Transactional(readOnly = true)
	public CategoriaDto findById(Long id) {
		Optional<Categoria> optional = repository.findById(id);
		Categoria categoria = optional.get();
		return (new CategoriaDto(categoria, categoria.getTopicos(), categoria.getSubcategorias()));
	}

}
