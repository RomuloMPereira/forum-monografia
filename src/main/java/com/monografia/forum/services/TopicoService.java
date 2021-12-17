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

import com.monografia.forum.dto.RespostaDto;
import com.monografia.forum.dto.TopicoDto;
import com.monografia.forum.dto.UsuarioPayloadDto;
import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Resposta;
import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.entities.Usuario;
import com.monografia.forum.repositories.CategoriaRepository;
import com.monografia.forum.repositories.RespostaRepository;
import com.monografia.forum.repositories.SubcategoriaRepository;
import com.monografia.forum.repositories.TopicoRepository;
import com.monografia.forum.repositories.UsuarioRepository;
import com.monografia.forum.services.exceptions.DatabaseException;
import com.monografia.forum.services.exceptions.EntidadeNaoEncontradaException;
import com.monografia.forum.services.exceptions.NaoAutorizadoException;

@Service
public class TopicoService {
	
	@Autowired
	private TopicoRepository repository; 
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private SubcategoriaRepository subcategoriaRepository;
	
	@Autowired
	private RespostaRepository respostaRepository;
	
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
			return new TopicoDto(topico);
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
		
		entity.getCurtidas().clear();
		for (UsuarioPayloadDto userDto : dto.getCurtidas()) {
			Optional<Usuario> optional4 = usuarioRepository.findById(userDto.getId());
			Usuario user = optional4.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
			entity.getCurtidas().add(user);
		}
		
		entity.getRespostas().clear();
		for(RespostaDto respostaDto : dto.getRespostas()) {
			Optional<Resposta> optional5 = respostaRepository.findById(respostaDto.getId());
			Resposta resposta = optional5.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
			entity.getRespostas().add(resposta);
		}
	}
}
