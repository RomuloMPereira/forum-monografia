package com.monografia.forum.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_resposta")
public class Resposta implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT")
	private String corpo;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant instante;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	
	@ManyToOne
	@JoinColumn(name = "topico_id")
	private Topico topico;
	
	@ManyToMany
	@JoinTable(name = "tb_respostas_curtidas",
		joinColumns = @JoinColumn(name = "resposta_id"),
		inverseJoinColumns = @JoinColumn(name = "usuario_id"))	
	private Set<Usuario> curtidas = new HashSet<>();

	public Resposta() {
	}
	
	public Resposta(Long id, String corpo, Instant instante, Usuario autor, Topico topico) {
		this.id = id;
		this.corpo = corpo;
		this.instante = instante;
		this.autor = autor;
		this.topico = topico;
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

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	public Set<Usuario> getCurtidas() {
		return curtidas;
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
		Resposta other = (Resposta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
