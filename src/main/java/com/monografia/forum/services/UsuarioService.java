package com.monografia.forum.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monografia.forum.dto.FuncaoDto;
import com.monografia.forum.dto.UsuarioDto;
import com.monografia.forum.entities.Funcao;
import com.monografia.forum.entities.Usuario;
import com.monografia.forum.repositories.FuncaoRepository;
import com.monografia.forum.repositories.UsuarioRepository;
import com.monografia.forum.services.exceptions.DatabaseException;
import com.monografia.forum.services.exceptions.EntidadeNaoEncontradaException;

@Service
public class UsuarioService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Page<UsuarioDto> findAllPaged(PageRequest pageRequest) {
		Page<Usuario> list = repository.findAll(pageRequest);
		return list.map(x -> new UsuarioDto(x));
	}

	@Transactional(readOnly = true)
	public UsuarioDto findById(Long id) {
		Optional<Usuario> optional = repository.findById(id);
		Usuario entity = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
		return new UsuarioDto(entity);
	}

	@Transactional
	public UsuarioDto insert(UsuarioDto dto) {
		Usuario entity = new Usuario();
		copyDtoToEntity(dto, entity);
		entity.setSenha(passwordEncoder.encode(dto.getSenha()));
		entity = repository.save(entity);
		return new UsuarioDto(entity);
	}

	@Transactional
	public UsuarioDto update(Long id, UsuarioDto dto) {
		try {
			Optional<Usuario> optional = repository.findById(id);
			Usuario entity = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UsuarioDto(entity);
		} catch (EntityNotFoundException e) {
			throw new EntidadeNaoEncontradaException("Entidade não encontrada");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
	}

	private void copyDtoToEntity(UsuarioDto dto, Usuario entity) {
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());
		entity.setSenha(passwordEncoder.encode(dto.getSenha()));

		entity.getFuncoes().clear();
		for (FuncaoDto funcaoDto : dto.getFuncoes()) {
			Optional<Funcao> optional = funcaoRepository.findById(funcaoDto.getId());
			Funcao funcao = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
			entity.getFuncoes().add(funcao);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario Usuario = repository.findByEmail(username);
		if (Usuario == null) {
			logger.error("Usuário não encontrado: " + username);
			throw new UsernameNotFoundException("Email não encontrado");
		}
		logger.info("Usuário encontrado: " + username);
		return Usuario;
	}

}