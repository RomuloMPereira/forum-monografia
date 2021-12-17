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
	private UsuarioPayloadDto autor;
	private TopicoDto topico;
	private List<UsuarioPayloadDto> curtidas = new ArrayList<>();

	public RespostaDto() {
	}

	public RespostaDto(Long id, String corpo, Instant instante, UsuarioPayloadDto autor, TopicoDto topico) {
		this.id = id;
		this.corpo = corpo;
		this.instante = instante;
		this.autor = autor;
		this.topico = topico;
	}
	
	public RespostaDto(Resposta entidade) {
		this.id = entidade.getId();
		this.corpo = entidade.getCorpo();
		this.instante = entidade.getInstante();
		this.autor = new UsuarioPayloadDto(entidade.getAutor());
		this.topico = new TopicoDto(entidade.getTopico());
	}
	
	public RespostaDto(Resposta entidade, Set<Usuario> curtidas) {
		this.id = entidade.getId();
		this.corpo = entidade.getCorpo();
		this.instante = entidade.getInstante();
		this.autor = new UsuarioPayloadDto(entidade.getAutor());
		this.topico = new TopicoDto(entidade.getTopico());
		
		curtidas.forEach(curtida -> this.curtidas.add(new UsuarioPayloadDto(curtida)));
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

	public UsuarioPayloadDto getAutor() {
		return autor;
	}

	public void setAutor(UsuarioPayloadDto autor) {
		this.autor = autor;
	}

	public TopicoDto getTopico() {
		return topico;
	}

	public void setTopico(TopicoDto topico) {
		this.topico = topico;
	}

	public List<UsuarioPayloadDto> getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(List<UsuarioPayloadDto> curtidas) {
		this.curtidas = curtidas;
	}
}
