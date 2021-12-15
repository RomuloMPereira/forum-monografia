package com.monografia.forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monografia.forum.entities.Categoria;
import com.monografia.forum.entities.Subcategoria;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long>{

	@Query("SELECT DISTINCT subcat from Subcategoria subcat "
			+ "WHERE subcat.categoria = :categoria")
	Page<Subcategoria> find(Categoria categoria, Pageable pageable);
}
