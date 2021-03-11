package com.monografia.forum.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@Column(unique = true)
	private String email;
	private String senha;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funcao_id")
	private Funcao funcao; 
	
	@OneToMany(mappedBy = "autor")
	private Set<Topico> topicos = new HashSet<>();
	
	@ManyToMany(mappedBy = "curtidas")
	private Set<Topico> topicosCurtidos = new HashSet<>();
	
	@OneToMany(mappedBy = "autor")
	private Set<Resposta> respostas = new HashSet<>();
	
	public Usuario() {
	}

	public Usuario(Long id, String nome, String email, String senha, Funcao funcao) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.funcao = funcao;
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

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public Set<Topico> getTopicos() {
		return topicos;
	}

	public Set<Topico> getTopicosCurtidos() {
		return topicosCurtidos;
	}

	public Set<Resposta> getRespostas() {
		return respostas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
