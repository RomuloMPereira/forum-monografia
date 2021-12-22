package com.monografia.forum.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
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

import com.monografia.forum.assemblers.TopicoModelAssembler;
import com.monografia.forum.dto.TopicoDto;
import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.repositories.CategoriaRepository;
import com.monografia.forum.repositories.SubcategoriaRepository;
import com.monografia.forum.repositories.TopicoRepository;
import com.monografia.forum.services.TopicoService;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoService service;	
	
	@Autowired
	private TopicoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private SubcategoriaRepository subcategoriaRepository;
	
	@Autowired 
	private TopicoModelAssembler topicoModelAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Topico> pagedResourcesAssembler;

	@GetMapping
	@Transactional
	public ResponseEntity<PagedModel<TopicoDto>> listar(@RequestParam(value = "titulo", defaultValue = "") String titulo,
			@RequestParam(value = "categoriaId", defaultValue = "0") Long categoriaId,
			@RequestParam(value = "subcategoriaId", defaultValue = "0") Long subcategoriaId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "3") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Topico> lista;
		if(titulo.isEmpty() && categoriaId == 0 && subcategoriaId == 0) {
			lista = repository.findAll(pageRequest);
		} else if (categoriaId == 0 && subcategoriaId == 0){
			lista = repository.find(titulo, pageRequest);
		} else if (subcategoriaId == 0){
			Optional<Categoria> optional = categoriaRepository.findById(categoriaId);
			Categoria categoria = optional.get();
			lista = repository.findByCategoria(categoria, pageRequest);
		} else {
			Optional<Subcategoria> optional = subcategoriaRepository.findById(subcategoriaId);
			Subcategoria subcategoria = optional.get();
			lista = repository.findBySubcategoria(subcategoria, pageRequest);
		}
		PagedModel<TopicoDto> pagedModel = pagedResourcesAssembler
				.toModel(lista, topicoModelAssembler);
		return ResponseEntity.ok().body(pagedModel);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TopicoDto> buscarPorId(@PathVariable Long id) {
		TopicoDto dto = service.buscarPorId(id);
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).listar(null, null, null, null, null, null, null));
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
