package com.monografia.forum.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public Page<SubcategoriaDto> listar(Long categoriaId, PageRequest pageRequest) {
		Page<Subcategoria> page;
		Page<SubcategoriaDto> pageDto;
		if(categoriaId == 0) {
			page = repository.findAll(pageRequest);
		} else {
			Optional<Categoria> optional = categoriaRepository.findById(categoriaId);
			Categoria categoria = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
			page = repository.find(categoria, pageRequest);
		}
		pageDto = page.map(x -> new SubcategoriaDto(x, x.getTopicos()));
		
		return pageDto;
	}

	@Transactional(readOnly = true)
	public SubcategoriaDto buscarPorId(Long id) {
		Optional<Subcategoria> optional = repository.findById(id);
		Subcategoria entidade = optional
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
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
		Optional<Subcategoria> optional = repository.findById(id);
		Subcategoria entidade = optional
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		copiarDtoParaEntidade(dto, entidade);
		entidade = repository.save(entidade);
		return new SubcategoriaDto(entidade);
	}

	private void copiarDtoParaEntidade(SubcategoriaDto dto, Subcategoria entidade) {
		entidade.setNome(dto.getNome());
		Optional<Categoria> optional = categoriaRepository.findById(dto.getCategoria().getId());
		Categoria categoria = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		entidade.setCategoria(categoria);

		entidade.getTopicos().clear();
		for (TopicoDto topicoDto : dto.getTopicos()) {
			Optional<Topico> opcional = topicoRepository.findById(topicoDto.getId());
			Topico topico = opcional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
			entidade.getTopicos().add(topico);
		}
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
}
