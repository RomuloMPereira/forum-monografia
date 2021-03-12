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
public class SubcategoriaService {

	@Autowired
	private SubcategoriaRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;

	@Transactional(readOnly = true)
	public List<SubcategoriaDto> listar() {
		List<Subcategoria> lista = repository.findAll();
		List<SubcategoriaDto> listaDto = new ArrayList<SubcategoriaDto>();
		for (Subcategoria entidade : lista) {
			listaDto.add(new SubcategoriaDto(entidade, entidade.getTopicos()));
		}
		return listaDto;
	}

	@Transactional(readOnly = true)
	public SubcategoriaDto buscarPorId(Long id) {
		Optional<Subcategoria> optional = repository.findById(id);
		Subcategoria entidade = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		return (new SubcategoriaDto(entidade, entidade.getTopicos()));
	}
	
	@Transactional
	public SubcategoriaDto cadastrar(SubcategoriaDto dto) {
		Subcategoria entidade = new Subcategoria();
		copiarDtoParaEntidade(dto, entidade);
		entidade = repository.save(entidade);
		return new SubcategoriaDto(entidade);
	}

	@Transactional
	public SubcategoriaDto atualizar(Long id, SubcategoriaDto dto) {
		try {
			Subcategoria entidade = repository.getOne(id);
			copiarDtoParaEntidade(dto, entidade);
			entidade = repository.save(entidade);
			return new SubcategoriaDto(entidade);
		} catch (EntityNotFoundException e) {
			throw new EntidadeNaoEncontradaException("Subcategoria com id " + id + " não foi encontrada");
		}

	}
	
	private void copiarDtoParaEntidade(SubcategoriaDto dto, Subcategoria entidade) {
		entidade.setNome(dto.getNome());
		Categoria categoria = categoriaRepository.getOne(dto.getCategoria().getId());
		entidade.setCategoria(categoria);
		
		entidade.getTopicos().clear();
		for (TopicoDto topicoDto : dto.getTopicos()) {
			Topico topico = topicoRepository.getOne(topicoDto.getId());
			entidade.getTopicos().add(topico);
		}
	}

	public void deletar(Long id) {
		try {
			repository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Categoria com id " + id + " não foi encontrada");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
	}
}
