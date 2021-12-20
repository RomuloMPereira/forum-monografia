package com.monografia.forum.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monografia.forum.dto.RespostaDto;
import com.monografia.forum.entities.Resposta;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.entities.Usuario;
import com.monografia.forum.repositories.RespostaRepository;
import com.monografia.forum.repositories.TopicoRepository;
import com.monografia.forum.services.exceptions.DatabaseException;
import com.monografia.forum.services.exceptions.EntidadeNaoEncontradaException;
import com.monografia.forum.services.exceptions.NaoAutorizadoException;

@Service
public class RespostaService {

	@Autowired
	private RespostaRepository repository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	@Transactional(readOnly = true)
	public RespostaDto buscarPorId(Long id){
		Optional<Resposta> optional = repository.findById(id);
		Resposta entity = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		return new RespostaDto(entity); 
	}
	
	@Transactional
	public RespostaDto cadastrar(Long topicoId, RespostaDto respostaDto, String username) {
		Optional<Topico> optional = topicoRepository.findById(topicoId);
		Topico topico = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		Resposta resposta = new Resposta();
		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(username);
		resposta.setCorpo(respostaDto.getCorpo());
		resposta.setInstante(Instant.now());
		resposta.setAutor(usuario);
		resposta.setTopico(topico);
		resposta = repository.save(resposta);
		return new RespostaDto(resposta);
	}

	@Transactional
	public RespostaDto atualizar(Long respostaId, RespostaDto dto, String username) {
		Optional<Resposta> optional = repository.findById(respostaId);
		Resposta resposta = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(username);
		if(resposta.getAutor().getId() == usuario.getId()) {
			resposta.setCorpo(dto.getCorpo());
			resposta.setInstante(Instant.now());
			resposta = repository.save(resposta);
			return new RespostaDto(resposta, resposta.getCurtidas());
		}
		throw new NaoAutorizadoException("Recurso não autorizado");
	}

	public void deletar(Long id, String username) {
		Optional<Resposta> optional = repository.findById(id);
		Resposta resposta = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(username);
		try {
			if(resposta.getAutor().getId() == usuario.getId()) {
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
	public RespostaDto curtir(Long respostaId, Long userId, String username) {
		Optional<Resposta> optional = repository.findById(respostaId);
		Resposta resposta = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(username);
		if(userId == usuario.getId()) {
			resposta.getCurtidas().add(usuario);
			resposta = repository.save(resposta);
			return new RespostaDto(resposta, resposta.getCurtidas());
		} else {
			throw new NaoAutorizadoException("Recurso não autorizado");
		}
	}

	@Transactional
	public RespostaDto descurtir(Long userId, Long respostaId, String username) {
		Optional<Resposta> optional = repository.findById(respostaId);
		Resposta resposta = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(username);
		if(userId == usuario.getId()) {
			resposta.getCurtidas().remove(usuario);
			resposta = repository.save(resposta);
			return new RespostaDto(resposta, resposta.getCurtidas());
		}
		throw new NaoAutorizadoException("Recurso não autorizado");
	}
}
