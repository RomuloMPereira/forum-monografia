package com.monografia.forum.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.monografia.forum.entities.Resposta;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.entities.Usuario;

public class TopicoDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String titulo;
	
	@NotBlank(message = "Campo obrigatório")
	private String corpo;
	private Instant instante;
	
	private UsuarioDto autor;
	private CategoriaDto categoria;	
	private SubcategoriaDto subcategoria;
	private List<UsuarioDto> curtidas = new ArrayList<>();
	private List<RespostaDto> respostas = new ArrayList<>();
	
	public TopicoDto() {
	}

	public TopicoDto(Long id, String titulo, String corpo, Instant instante, UsuarioDto autor, CategoriaDto categoria, SubcategoriaDto subcategoria) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.corpo = corpo;
		this.instante = instante;
		this.autor = autor;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
	}
	
	public TopicoDto(Topico entidade) {
		super();
		this.id = entidade.getId();
		this.titulo = entidade.getTitulo();
		this.corpo = entidade.getCorpo();
		this.instante = entidade.getInstante();
		this.autor = new UsuarioDto(entidade.getAutor());
		this.categoria = new CategoriaDto(entidade.getCategoria());
		this.subcategoria = new SubcategoriaDto(entidade.getSubcategoria());
	}
	
	public TopicoDto(Topico entidade, Set<Usuario> curtidas, Set<Resposta> respostas) {
		super();
		this.id = entidade.getId();
		this.titulo = entidade.getTitulo();
		this.corpo = entidade.getCorpo();
		this.instante = entidade.getInstante();
		this.autor = new UsuarioDto(entidade.getAutor());
		this.categoria = new CategoriaDto(entidade.getCategoria());
		this.subcategoria = new SubcategoriaDto(entidade.getSubcategoria());
		
		curtidas.forEach(curtida -> this.curtidas.add(new UsuarioDto(curtida)));
		respostas.forEach(resposta -> this.respostas.add(new RespostaDto(resposta)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public CategoriaDto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDto categoria) {
		this.categoria = categoria;
	}

	public SubcategoriaDto getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(SubcategoriaDto subcategoria) {
		this.subcategoria = subcategoria;
	}

	public List<UsuarioDto> getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(List<UsuarioDto> curtidas) {
		this.curtidas = curtidas;
	}

	public List<RespostaDto> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<RespostaDto> respostas) {
		this.respostas = respostas;
	}
}
