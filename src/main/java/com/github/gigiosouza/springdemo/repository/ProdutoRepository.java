package com.github.gigiosouza.springdemo.repository;

import com.github.gigiosouza.springdemo.models.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

  @Query("SELECT p FROM Produto p WHERE p.ativo = true")
  List<Produto> listarTodosAtivos();

  @Query("SELECT p FROM Produto p WHERE p.ativo = ?1")
  List<Produto> listarPorAtivo(Boolean ativo);

  List<Produto> findAllByAtivo(Boolean ativo);

  Page<Produto> findAll(Pageable pageRequest);

  Page<Produto> findAll(Specification specification, Pageable pageRequest);
}
