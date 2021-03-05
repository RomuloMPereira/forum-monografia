package com.monografia.forum.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.monografia.forum.entities.Resposta;
import com.monografia.forum.entities.Usuario;

public class RespostaDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String corpo;
	private Instant instante;
	private UsuarioDto autor;
	private TopicoDto topico;
	private List<UsuarioDto> curtidas = new ArrayList<>();

	public RespostaDto() {
	}

	public RespostaDto(Long id, String corpo, Instant instante, UsuarioDto autor, TopicoDto topico) {
		super();
		this.id = id;
		this.corpo = corpo;
		this.instante = instante;
		this.autor = autor;
		this.topico = topico;
	}
	
	public RespostaDto(Resposta entidade) {
		super();
		this.id = entidade.getId();
		this.corpo = entidade.getCorpo();
		this.instante = entidade.getInstante();
		this.autor = new UsuarioDto(entidade.getAutor());
		this.topico = new TopicoDto(entidade.getTopico());
	}
	
	public RespostaDto(Resposta entidade, Set<Usuario> curtidas) {
		super();
		this.id = entidade.getId();
		this.corpo = entidade.getCorpo();
		this.instante = entidade.getInstante();
		this.autor = new UsuarioDto(entidade.getAutor());
		this.topico = new TopicoDto(entidade.getTopico());
		
		curtidas.forEach(curtida -> this.curtidas.add(new UsuarioDto(curtida)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public Instant getInstante() {
		return instante;
	}

	public void setInstante(Instant instante) {
		this.instante = instante;
	}

	public UsuarioDto getAutor() {
		return autor;
	}

	public void setAutor(UsuarioDto autor) {
		this.autor = autor;
	}

	public TopicoDto getTopico() {
		return topico;
	}

	public void setTopico(TopicoDto topico) {
		this.topico = topico;
	}

	public List<UsuarioDto> getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(List<UsuarioDto> curtidas) {
		this.curtidas = curtidas;
	}
}
