package com.monografia.forum.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monografia.forum.dto.TopicoDto;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.repositories.TopicoRepository;
import com.monografia.forum.services.exceptions.EntidadeNaoEncontradaException;

@Service
public class TopicoService {
	
	@Autowired
	private TopicoRepository repository; 
	
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
}
