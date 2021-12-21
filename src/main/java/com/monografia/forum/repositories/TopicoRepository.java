package com.monografia.forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monografia.forum.entities.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>{

	@Query(value="SELECT DISTINCT topico FROM Topico topico WHERE "
			+ "(LOWER(topico.titulo) LIKE LOWER(CONCAT('%',:titulo,'%')))", nativeQuery = true)
	Page<Topico> find(String titulo, Pageable pageable);
}
