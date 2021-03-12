package com.monografia.forum.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.entities.Topico;

public class SubcategoriaDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String nome;
	private CategoriaDto categoria;
	private List<TopicoDto> topicos = new ArrayList<>();
	
	public SubcategoriaDto() {
		
	}

	public SubcategoriaDto(Long id, String nome, CategoriaDto categoria) {
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
	}
	
	public SubcategoriaDto(Subcategoria entidade) {
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		this.categoria = new CategoriaDto(entidade.getCategoria());
	}
	
	public SubcategoriaDto(Subcategoria entidade, Set<Topico> topicos) {
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		this.categoria = new CategoriaDto(entidade.getCategoria());
		
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

	public CategoriaDto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDto categoria) {
		this.categoria = categoria;
	}

	public List<TopicoDto> getTopicos() {
		return topicos;
	}

	public void setTopicos(List<TopicoDto> topicos) {
		this.topicos = topicos;
	}
}
