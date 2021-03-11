package com.monografia.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monografia.forum.entities.Funcao;

@Repository
public interface FuncaoRepository extends JpaRepository<Funcao, Long>{

}
