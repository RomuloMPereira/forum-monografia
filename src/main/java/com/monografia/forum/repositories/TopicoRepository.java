package com.monografia.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monografia.forum.entities.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>{

}
