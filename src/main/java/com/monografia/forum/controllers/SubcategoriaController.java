package com.monografia.forum.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.monografia.forum.dto.SubcategoriaDto;
import com.monografia.forum.services.SubcategoriaService;

@RestController
@RequestMapping(value = "/subcategorias")
public class SubcategoriaController {

	@Autowired
	private SubcategoriaService service;

	@GetMapping
	public ResponseEntity<List<SubcategoriaDto>> listar() {
		List<SubcategoriaDto> lista = service.listar();
		return ResponseEntity.ok().body(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<SubcategoriaDto> buscarPorId(@PathVariable Long id) {
		SubcategoriaDto dto = service.buscarPorId(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<SubcategoriaDto> cadastrar(@Valid @RequestBody SubcategoriaDto dto) {
		dto = service.cadastrar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<SubcategoriaDto> atualizar(@PathVariable Long id, @Valid @RequestBody SubcategoriaDto dto){
		dto = service.atualizar(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<SubcategoriaDto> deletar(@PathVariable Long id){
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
