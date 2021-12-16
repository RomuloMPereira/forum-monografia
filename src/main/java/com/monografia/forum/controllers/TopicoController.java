package com.monografia.forum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monografia.forum.dto.TopicoDto;
import com.monografia.forum.services.TopicoService;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoService service;
	
	@GetMapping
	private ResponseEntity<Page<TopicoDto>> listar(
			@RequestParam(value = "titulo", defaultValue = "") String titulo,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<TopicoDto> lista = service.listar(titulo, pageRequest);
		return ResponseEntity.ok().body(lista);
	}
}
