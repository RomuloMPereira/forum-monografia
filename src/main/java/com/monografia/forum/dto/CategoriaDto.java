package com.monografia.forum.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Topico;

public class CategoriaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private List<TopicoDto> topicos = new ArrayList<>();
	
	public CategoriaDto() {
	}

	public CategoriaDto(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public CategoriaDto(Categoria entidade) {
		super();
		this.id = entidade.getId();
		this.nome = entidade.getNome();
	}
	
	public CategoriaDto(Categoria entidade, Set<Topico> topicos) {
		super();
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		
		topicos.forEach(topico -> this.topicos.add(new TopicoDto(topico)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<TopicoDto> getTopicos() {
		return topicos;
	}

	public void setTopicos(List<TopicoDto> topicos) {
		this.topicos = topicos;
	}
}
