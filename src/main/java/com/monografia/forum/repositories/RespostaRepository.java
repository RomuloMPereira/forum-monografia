package com.monografia.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monografia.forum.entities.Resposta;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long>{

}
