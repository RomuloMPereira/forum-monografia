package com.monografia.forum.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monografia.forum.dto.CategoriaDto;
import com.monografia.forum.entities.Categoria;
import com.monografia.forum.repositories.CategoriaRepository;
import com.monografia.forum.services.exceptions.DatabaseException;
import com.monografia.forum.services.exceptions.EntidadeNaoEncontradaException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoriaDto> listar(PageRequest pageRequest) {
		Page<Categoria> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoriaDto(x));
	}

	@Transactional(readOnly = true)
	public CategoriaDto buscarPorId(Long id) {
		Optional<Categoria> optional = repository.findById(id);
		Categoria categoria = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		return (new CategoriaDto(categoria, categoria.getSubcategorias()));
	}

	@Transactional
	public CategoriaDto cadastrar(CategoriaDto dto) {
		Categoria entidade = new Categoria();
		copiarDtoParaEntidade(dto, entidade);
		entidade = repository.save(entidade);
		return new CategoriaDto(entidade);
	}

	@Transactional
	public CategoriaDto atualizar(Long id, CategoriaDto dto) {
		Optional<Categoria> optional = repository.findById(id);
		Categoria entidade = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		copiarDtoParaEntidade(dto, entidade);
		entidade = repository.save(entidade);
		return new CategoriaDto(entidade);
	}

	public void deletar(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Categoria com id " + id + " não foi encontrada");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
	}

	private void copiarDtoParaEntidade(CategoriaDto dto, Categoria entity) {
		entity.setNome(dto.getNome());
	}
}
