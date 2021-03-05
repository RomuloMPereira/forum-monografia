package com.monografia.forum.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.monografia.forum.entities.Resposta;
import com.monografia.forum.entities.Topico;
import com.monografia.forum.entities.Usuario;

public class UsuarioDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String nome;
	
	@Email(message = "Favor entrar um email válido")
	private String email;
	private String senha;
	
	private FuncaoDto funcao; 
	private List<TopicoDto> topicos = new ArrayList<>();
	private List<TopicoDto> topicosCurtidos = new ArrayList<>();
	private List<RespostaDto> respostas = new ArrayList<>();
	
	public UsuarioDto() {
	}

	public UsuarioDto(Long id, String nome, String email, String senha, FuncaoDto funcao) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.funcao = funcao;
	}
	
	public UsuarioDto(Usuario entidade) {
		super();
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		this.email = entidade.getEmail();
		this.senha = entidade.getSenha();
		this.funcao = new FuncaoDto(entidade.getFuncao());
	}
	
	public UsuarioDto(Usuario entidade, Set<Topico> topicos, Set<Topico> topicosCurtidos, Set<Resposta> respostas) {
		super();
		this.id = entidade.getId();
		this.nome = entidade.getNome();
		this.email = entidade.getEmail();
		this.senha = entidade.getSenha();
		this.funcao = new FuncaoDto(entidade.getFuncao());
		
		topicos.forEach(topico -> this.topicos.add(new TopicoDto(topico)));
		topicosCurtidos.forEach(topicoCurtido -> this.topicosCurtidos.add(new TopicoDto(topicoCurtido)));
		respostas.forEach(resposta -> this.respostas.add(new RespostaDto(resposta)));
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public FuncaoDto getFuncao() {
		return funcao;
	}

	public void setFuncao(FuncaoDto funcao) {
		this.funcao = funcao;
	}

	public List<TopicoDto> getTopicos() {
		return topicos;
	}

	public void setTopicos(List<TopicoDto> topicos) {
		this.topicos = topicos;
	}

	public List<TopicoDto> getTopicosCurtidos() {
		return topicosCurtidos;
	}

	public void setTopicosCurtidos(List<TopicoDto> topicosCurtidos) {
		this.topicosCurtidos = topicosCurtidos;
	}

	public List<RespostaDto> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<RespostaDto> respostas) {
		this.respostas = respostas;
	}
}
