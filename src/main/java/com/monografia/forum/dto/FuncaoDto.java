package com.monografia.forum.dto;

import java.io.Serializable;

import com.monografia.forum.entities.Funcao;

public class FuncaoDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String autoridade;
	
	public FuncaoDto() {
	}

	public FuncaoDto(Long id, String autoridade) {
		super();
		this.id = id;
		this.autoridade = autoridade;
	}
	
	public FuncaoDto(Funcao entidade) {
		super();
		this.id = entidade.getId();
		this.autoridade = entidade.getAutoridade();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAutoridade() {
		return autoridade;
	}

	public void setAutoridade(String autoridade) {
		this.autoridade = autoridade;
	}
}
