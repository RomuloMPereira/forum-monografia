package com.monografia.forum.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monografia.forum.dto.RespostaDto;
import com.monografia.forum.entities.Resposta;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.entities.Usuario;
import com.monografia.forum.repositories.RespostaRepository;
import com.monografia.forum.repositories.TopicoRepository;
import com.monografia.forum.services.exceptions.EntidadeNaoEncontradaException;

@Service
public class RespostaService {

	@Autowired
	private RespostaRepository repository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private UsuarioService usuarioService;

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
}
