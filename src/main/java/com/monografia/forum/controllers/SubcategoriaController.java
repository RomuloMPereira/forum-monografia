package com.monografia.forum.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
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

import com.monografia.forum.dto.SubcategoriaDto;
import com.monografia.forum.services.SubcategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class SubcategoriaController {

	@Autowired
	private SubcategoriaService service;

	@GetMapping(value = "/subcategorias")
	public ResponseEntity<Page<SubcategoriaDto>> listar(
			@RequestParam(value = "categoriaId", defaultValue = "0") Long categoriaId,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<SubcategoriaDto> lista = service.listar(categoriaId, pageRequest);
		lista.stream().forEach(x -> {
			WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).buscarPorId(x.getId(), x.getCategoria().getId()));
			x.add(linkTo.withRel("subcategoria"));
		});
		return ResponseEntity.ok().body(lista);
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
