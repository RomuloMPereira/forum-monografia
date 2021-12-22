package com.monografia.forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Subcategoria;
import com.monografia.forum.entities.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>{

	@Query("SELECT DISTINCT t FROM Topico t WHERE "
			+ "(:titulo = '' OR LOWER(t.titulo) LIKE LOWER(CONCAT('%',:titulo,'%')))")
	Page<Topico> find(String titulo, Pageable pageable);
	
	Page<Topico> findByCategoria(Categoria categoria, Pageable pageable);
	
	Page<Topico> findBySubcategoria(Subcategoria categoria, Pageable pageable);
}
