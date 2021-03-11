package com.monografia.forum.services;

import java.util.ArrayList;
import java.util.List;

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
			listDto.add(new CategoriaDto(entity));
		}
		return listDto;
	}

}
