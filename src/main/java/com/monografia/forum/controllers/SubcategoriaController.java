package com.monografia.forum.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Optional;

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

import com.monografia.forum.assemblers.SubcategoriaModelAssembler;
import com.monografia.forum.dto.SubcategoriaDto;
import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.repositories.CategoriaRepository;
import com.monografia.forum.repositories.SubcategoriaRepository;
import com.monografia.forum.services.SubcategoriaService;
import com.monografia.forum.services.exceptions.EntidadeNaoEncontradaException;

@RestController
@RequestMapping(value = "/categorias")
public class SubcategoriaController {

	@Autowired
	private SubcategoriaService service;
	
	@Autowired
	private SubcategoriaRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private SubcategoriaModelAssembler subcategoriaModelAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Subcategoria> pagedResourcesAssembler;

	@GetMapping(value = "/subcategorias")
	@Transactional
	public ResponseEntity<PagedModel<SubcategoriaDto>> listar(
			@RequestParam(value = "categoriaId", defaultValue = "0") Long categoriaId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "3") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<Subcategoria> lista;
		if(categoriaId == 0) {
			lista = repository.findAll(pageRequest);
		} else {
			Optional<Categoria> optional = categoriaRepository.findById(categoriaId);
			Categoria categoria = optional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));
			lista = repository.find(categoria, pageRequest);
		}
		PagedModel<SubcategoriaDto> pagedModel = pagedResourcesAssembler
				.toModel(lista, subcategoriaModelAssembler);
		return ResponseEntity.ok().body(pagedModel);
	}

	@GetMapping(value = "/{categoriaId}/subcategorias/{id}")
	public ResponseEntity<SubcategoriaDto> buscarPorId(@PathVariable Long id, @PathVariable Long categoriaId) {
		SubcategoriaDto dto = service.buscarPorId(id);
		if(dto.getCategoria().getId() == categoriaId) {
			WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listar(categoriaId, null, null, null, null));
			dto.add(linkTo.withRel("todas-subcategorias-categoria-" + categoriaId));
			return ResponseEntity.ok().body(dto);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/{categoriaId}/subcategorias")
	public ResponseEntity<SubcategoriaDto> cadastrar(@Valid @RequestBody SubcategoriaDto dto, @PathVariable Long categoriaId) {
		if(dto.getCategoria().getId() == categoriaId) {
			dto = service.cadastrar(dto);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(dto.getId()).toUri();
			return ResponseEntity.created(uri).body(dto);
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/{categoriaId}/subcategorias/{id}")
	public ResponseEntity<SubcategoriaDto> atualizar(@PathVariable Long id, @PathVariable Long categoriaId, @Valid @RequestBody SubcategoriaDto dto){
		if(dto.getCategoria().getId() == categoriaId) {
			dto = service.atualizar(id, dto);
			return ResponseEntity.ok().body(dto);
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping(value = "/{categoriaId}/subcategorias/{id}")
	public ResponseEntity<SubcategoriaDto> deletar(@PathVariable Long id, @PathVariable Long categoriaId){
		SubcategoriaDto dto = service.buscarPorId(id);
		if(dto.getCategoria().getId() == categoriaId) {
			service.deletar(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.badRequest().build();
	}
}
