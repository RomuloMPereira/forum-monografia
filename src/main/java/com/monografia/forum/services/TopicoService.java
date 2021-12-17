package com.monografia.forum.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monografia.forum.dto.TopicoDto;
import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.entities.Usuario;
import com.monografia.forum.repositories.CategoriaRepository;
import com.monografia.forum.repositories.SubcategoriaRepository;
import com.monografia.forum.repositories.TopicoRepository;
import com.monografia.forum.services.exceptions.DatabaseException;
import com.monografia.forum.services.exceptions.EntidadeNaoEncontradaException;
import com.monografia.forum.services.exceptions.NaoAutorizadoException;

@Service
public class TopicoService {
	
	@Autowired
	private TopicoRepository repository; 
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private SubcategoriaRepository subcategoriaRepository;
	
	@Transactional(readOnly = true)
	public Page<TopicoDto> listar(String titulo, PageRequest pageRequest){
		Page<Topico> page;
		if(titulo == "") {
			page = repository.findAll(pageRequest);
		} else {
			page = repository.find(titulo, pageRequest);
		}
		return page.map(x -> new TopicoDto(x));
	}
	
	@Transactional(readOnly = true)
	public TopicoDto buscarPorId(Long id){
		Optional<Topico> optional = repository.findById(id);
		Topico entity = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		return new TopicoDto(entity, entity.getCurtidas(), entity.getRespostas()); 
	}
	
	@Transactional
	public TopicoDto cadastrar(TopicoDto dto, String username) {
		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(username);
		Topico topico = new Topico();
		topico.setAutor(usuario);
		copyDtoToEntity(dto, topico);
		if(topico.getCategoria() == null) {
			Optional<Categoria> optional = categoriaRepository.findById(1L);
			Categoria categoria = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
			topico.setCategoria(categoria);
		}
		if(topico.getSubcategoria() == null) {
			Optional<Subcategoria> optional = subcategoriaRepository.findById(1L);
			Subcategoria subcategoria = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
			topico.setSubcategoria(subcategoria);
		}
		topico = repository.save(topico);
		return new TopicoDto(topico);
	}
	
	@Transactional
	public TopicoDto atualizar(Long id, TopicoDto dto, String username) {
		Optional<Topico> optional = repository.findById(id);
		Topico topico = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		Usuario user = (Usuario) usuarioService.loadUserByUsername(username);
		if(topico.getAutor().getId() == user.getId()) {
			copyDtoToEntity(dto, topico);
			topico = repository.save(topico);
			return new TopicoDto(topico, topico.getCurtidas(), topico.getRespostas());
		} else {
			throw new NaoAutorizadoException("Recurso não autorizado");
		}
	}
	
	public void deletar(Long id, String username) {
		Optional<Topico> optional = repository.findById(id);
		Topico topico = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		Usuario user = (Usuario) usuarioService.loadUserByUsername(username);
		try {
			if(topico.getAutor().getId() == user.getId()) {
				repository.deleteById(id);
			} else {
				throw new NaoAutorizadoException("Recurso não autorizado");
			}
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
	}
	
	@Transactional
	public TopicoDto curtir(Long userId, Long topicoId, String username) {
		Optional<Topico> optional = repository.findById(topicoId);
		Topico topico = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		Usuario user = (Usuario) usuarioService.loadUserByUsername(username);
		if(userId == user.getId()) {
			topico.getCurtidas().add(user);
			topico = repository.save(topico);
			return new TopicoDto(topico, topico.getCurtidas(), topico.getRespostas());
		}
		throw new NaoAutorizadoException("Recurso não autorizado");
	}

	@Transactional
	public TopicoDto descurtir(Long userId, Long topicoId, String username) {
		Optional<Topico> optional = repository.findById(topicoId);
		Topico topico = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		Usuario user = (Usuario) usuarioService.loadUserByUsername(username);
		if(userId == user.getId()) {
			topico.getCurtidas().remove(user);
			topico = repository.save(topico);
			return new TopicoDto(topico, topico.getCurtidas(), topico.getRespostas());
		}
		throw new NaoAutorizadoException("Recurso não autorizado");
	}
	
	private void copyDtoToEntity(TopicoDto dto, Topico entity) {
		entity.setTitulo(dto.getTitulo());
		entity.setCorpo(dto.getCorpo());
		entity.setInstante(Instant.now());
		
		Optional<Categoria> optional2 = categoriaRepository.findById(dto.getCategoria().getId());
		Categoria categoria = optional2.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		entity.setCategoria(categoria);
		
		Optional<Subcategoria> optional3 = subcategoriaRepository.findById(dto.getSubcategoria().getId());
		Subcategoria subcategoria = optional3.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		entity.setSubcategoria(subcategoria);
	}
}
