package com.monografia.forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monografia.forum.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);
	
	@Query("SELECT DISTINCT user from Usuario user WHERE "
			+ "(LOWER(user.nome) LIKE LOWER(CONCAT('%',:nome,'%')))")
	Page<Usuario> find(String nome, Pageable pageable);
}
