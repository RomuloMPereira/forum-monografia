package com.monografia.forum.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.monografia.forum.dto.RespostaDto;
import com.monografia.forum.services.RespostaService;

@RestController
@RequestMapping("/topicos")
public class RespostaController {

	@Autowired
	private RespostaService service;

	@PostMapping(value = "/{topicoId}/respostas")
	public ResponseEntity<RespostaDto> cadastrar(@PathVariable Long topicoId, @RequestBody RespostaDto dto,
			@AuthenticationPrincipal String username) {
		dto = service.cadastrar(topicoId, dto, username);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{topicoId}/respostas/{respostaId}")
	public ResponseEntity<RespostaDto> atualizar(@PathVariable Long topicoId, @PathVariable Long respostaId,
			@RequestBody RespostaDto respostaDto, @AuthenticationPrincipal String username) {
		RespostaDto dto = service.buscarPorId(respostaId);
		if (dto.getTopico().getId() == topicoId) {
			dto = service.atualizar(respostaId, respostaDto, username);
			return ResponseEntity.ok().body(dto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(value = "/{topicoId}/respostas/{respostaId}/usuarios/{userId}")
	public ResponseEntity<RespostaDto> curtir(@PathVariable Long userId, @PathVariable Long topicoId,
			@PathVariable Long respostaId, @AuthenticationPrincipal String username) {
		RespostaDto respostaDto = service.buscarPorId(respostaId);
		if (respostaDto.getTopico().getId() == topicoId) {
			respostaDto = service.curtir(respostaId, userId, username);
			return ResponseEntity.ok().body(respostaDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/{topicoId}/respostas/{respostaId}")
	public ResponseEntity<RespostaDto> deletar(@PathVariable Long topicoId, @PathVariable Long respostaId,
			@AuthenticationPrincipal String username) {
		RespostaDto dto = service.buscarPorId(respostaId);
		if (dto.getTopico().getId() == topicoId) {
			service.deletar(respostaId, username);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/{topicoId}/respostas/{respostaId}/usuarios/{userId}")
	public ResponseEntity<RespostaDto> descurtir(@PathVariable Long topicoId, @PathVariable Long respostaId,
			@PathVariable Long userId, @AuthenticationPrincipal String username) {
		RespostaDto dto = service.buscarPorId(respostaId);
		if (dto.getTopico().getId() == topicoId) {
			dto = service.descurtir(userId, respostaId, username);
			return ResponseEntity.ok().body(dto);
		}
		return ResponseEntity.notFound().build();
	}
}
