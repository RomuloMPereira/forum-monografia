package com.monografia.forum.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
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

import com.monografia.forum.assemblers.CategoriaModelAssembler;
import com.monografia.forum.dto.CategoriaDto;
import com.monografia.forum.entities.Categoria;
import com.monografia.forum.repositories.CategoriaRepository;
import com.monografia.forum.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;
	
	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private CategoriaModelAssembler categoriaModelAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Categoria> pagedResourcesAssembler;

	@GetMapping
	@Transactional
	public ResponseEntity<PagedModel<CategoriaDto>> listar(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "3") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Categoria> lista = repository.findAll(pageRequest);
		PagedModel<CategoriaDto> pagedModel = pagedResourcesAssembler
				.toModel(lista, categoriaModelAssembler);
		
		return ResponseEntity.ok().body(pagedModel);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoriaDto> buscarPorId(@PathVariable Long id) {
		CategoriaDto dto = service.buscarPorId(id);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listar(null, null, null, null));
		dto.add(linkTo.withRel("todas-categorias"));
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<CategoriaDto> cadastrar(@Valid @RequestBody CategoriaDto dto) {
		dto = service.cadastrar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoriaDto> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDto dto){
		dto = service.atualizar(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CategoriaDto> deletar(@PathVariable Long id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
