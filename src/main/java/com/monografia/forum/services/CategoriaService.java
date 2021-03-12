package com.monografia.forum.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monografia.forum.dto.CategoriaDto;
import com.monografia.forum.dto.SubcategoriaDto;
import com.monografia.forum.dto.TopicoDto;
import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.repositories.CategoriaRepository;
import com.monografia.forum.repositories.SubcategoriaRepository;
import com.monografia.forum.repositories.TopicoRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private SubcategoriaRepository subcategoriaRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
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

	@Transactional
	public CategoriaDto insert(CategoriaDto dto) {
		Categoria entity = new Categoria();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CategoriaDto(entity);
	}

	private void copyDtoToEntity(CategoriaDto dto, Categoria entity) {
		entity.setNome(dto.getNome());
		
		entity.getSubcategorias().clear();
		for (SubcategoriaDto subcategoriaDto : dto.getSubcategorias()) {
			Subcategoria subcategoria = subcategoriaRepository.getOne(subcategoriaDto.getId());
			entity.getSubcategorias().add(subcategoria);
		}
		
		entity.getTopicos().clear();
		for (TopicoDto topicoDto : dto.getTopicos()) {
			Topico topico = topicoRepository.getOne(topicoDto.getId());
			entity.getTopicos().add(topico);
		}
	}
}
