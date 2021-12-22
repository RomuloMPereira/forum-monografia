package com.monografia.forum.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import com.monografia.forum.entities.Subcategoria;

public class SubcategoriaDto extends RepresentationModel<SubcategoriaDto> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String nome;
	private CategoriaDto categoria;
	
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
}
