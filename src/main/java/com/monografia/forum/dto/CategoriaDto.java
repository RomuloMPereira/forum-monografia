package com.monografia.forum.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.entities.Topico;

public class CategoriaDto extends RepresentationModel<CategoriaDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String nome;
	private List<TopicoDto> topicos = new ArrayList<>();
	private List<SubcategoriaDto> subcategorias = new ArrayList<>();
	
	public CategoriaDto() {
	}

	public CategoriaDto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public CategoriaDto(Categoria entidade) {
		this.id = entidade.getId();
		this.nome = entidade.getNome();
	}
	
	public CategoriaDto(Categoria entidade, Set<Topico> topicos, Set<Subcategoria> subcategorias) {
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		
		topicos.forEach(topico -> this.topicos.add(new TopicoDto(topico)));
		subcategorias.forEach(subcategoria -> this.subcategorias.add(new SubcategoriaDto(subcategoria)));
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

	public List<SubcategoriaDto> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<SubcategoriaDto> subcategorias) {
		this.subcategorias = subcategorias;
	}
}
