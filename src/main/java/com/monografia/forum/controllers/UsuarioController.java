package com.monografia.forum.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.monografia.forum.dto.UsuarioDto;
import com.monografia.forum.dto.UsuarioPayloadDto;
import com.monografia.forum.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	private ResponseEntity<Page<UsuarioPayloadDto>> listar(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<UsuarioPayloadDto> lista = service.findAllPaged(nome, pageRequest);
		return ResponseEntity.ok().body(lista);
	}
	
	@GetMapping(value = "/{id}")
	private ResponseEntity<UsuarioPayloadDto> buscarPorId(@PathVariable Long id){
		UsuarioPayloadDto dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	private ResponseEntity<UsuarioPayloadDto> cadastrar(@Valid @RequestBody UsuarioDto dto){
		UsuarioPayloadDto dtoPayload = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dtoPayload);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UsuarioPayloadDto> atualizar(@PathVariable Long id, 
			@Valid @RequestBody UsuarioDto dto, @AuthenticationPrincipal String username){
		if(service.confirmarUsuario(id, username)) {
			UsuarioPayloadDto newDto = service.update(id, dto);
			return ResponseEntity.ok().body(newDto);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<UsuarioPayloadDto> deletar(@PathVariable Long id,
			@AuthenticationPrincipal String username) {
		if(service.confirmarUsuario(id, username)) {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
