package com.monografia.forum.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.entities.Topico;

public class SubcategoriaDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String nome;
	private Categoria categoria;
	private List<TopicoDto> topicos = new ArrayList<>();
	
	public SubcategoriaDto() {
		
	}

	public SubcategoriaDto(Long id, String nome, Categoria categoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
	}
	
	public SubcategoriaDto(Subcategoria entity) {
		super();
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.categoria = entity.getCategoria();
	}
	
	public SubcategoriaDto(Subcategoria entity, Set<Topico> topicos) {
		super();
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.categoria = entity.getCategoria();
		
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<TopicoDto> getTopicos() {
		return topicos;
	}

	public void setTopicos(List<TopicoDto> topicos) {
		this.topicos = topicos;
	}
}
