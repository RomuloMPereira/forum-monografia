package com.monografia.forum.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import com.monografia.forum.dto.TopicoDto;
import com.monografia.forum.services.TopicoService;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoService service;	

	@GetMapping
	public ResponseEntity<Page<TopicoDto>> listar(@RequestParam(value = "titulo", defaultValue = "") String titulo,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<TopicoDto> lista = service.listar(titulo, pageRequest);
		lista.stream().forEach(x -> {
			WebMvcLinkBuilder linkTo = 
					linkTo(methodOn(this.getClass()).buscarPorId(x.getId()));
			x.add(linkTo.withRel("topico"));
		});			
		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TopicoDto> buscarPorId(@PathVariable Long id) {
		TopicoDto dto = service.buscarPorId(id);
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).listar(null, null, null, null, null));
		dto.add(linkTo.withRel("todos-topicos"));
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody TopicoDto dto, @AuthenticationPrincipal String username) {
		TopicoDto dtoPayload = service.cadastrar(dto, username);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dtoPayload);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody TopicoDto dto,
			@AuthenticationPrincipal String username) {
		dto = service.atualizar(id, dto, username);
		return ResponseEntity.ok().body(dto);
	}

	@PutMapping(value = "/{topicoId}/usuarios/{userId}")
	public ResponseEntity<TopicoDto> curtir(@PathVariable Long userId, @PathVariable Long topicoId,
			@AuthenticationPrincipal String username) {
		TopicoDto dto = service.curtir(userId, topicoId, username);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TopicoDto> deletar(@PathVariable Long id, @AuthenticationPrincipal String username) {
		service.deletar(id, username);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{topicoId}/usuarios/{userId}")
	public ResponseEntity<TopicoDto> descurtir(@PathVariable Long topicoId, @PathVariable Long userId,
			@AuthenticationPrincipal String username) {
		TopicoDto dto = service.descurtir(userId, topicoId, username);
		return ResponseEntity.ok().body(dto);
	}
}
