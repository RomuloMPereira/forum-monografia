package com.monografia.forum.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
import com.monografia.forum.services.exceptions.DatabaseException;
import com.monografia.forum.services.exceptions.EntidadeNaoEncontradaException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	@Autowired
	private SubcategoriaRepository subcategoriaRepository;

	@Autowired
	private TopicoRepository topicoRepository;

	@Transactional(readOnly = true)
	public List<CategoriaDto> listar() {
		List<Categoria> lista = repository.findAll();
		List<CategoriaDto> listaDto = new ArrayList<CategoriaDto>();
		for (Categoria entidade : lista) {
			listaDto.add(new CategoriaDto(entidade, entidade.getTopicos(), entidade.getSubcategorias()));
		}
		return listaDto;
	}

	@Transactional(readOnly = true)
	public CategoriaDto buscarPorId(Long id) {
		Optional<Categoria> optional = repository.findById(id);
		Categoria categoria = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		return (new CategoriaDto(categoria, categoria.getTopicos(), categoria.getSubcategorias()));
	}

	@Transactional
	public CategoriaDto cadastrar(CategoriaDto dto) {
		Categoria entidade = new Categoria();
		copyDtoToEntity(dto, entidade);
		entidade = repository.save(entidade);
		return new CategoriaDto(entidade);
	}

	@Transactional
	public CategoriaDto atualizar(Long id, CategoriaDto dto) {
		try {
			Categoria entidade = repository.getOne(id);
			copyDtoToEntity(dto, entidade);
			entidade = repository.save(entidade);
			return new CategoriaDto(entidade);
		} catch (EntityNotFoundException e) {
			throw new EntidadeNaoEncontradaException("Categoria com id " + id + " não foi encontrada");
		}

	}
	
	public void deletar(Long id) {
		try {
			
		} catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Categoria com id " + id + " não foi encontrada");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
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
