package com.github.gigiosouza.springdemo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "produtos")
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "categoria", nullable = false)
  private String categoria;

  @Column(name = "ativo", nullable = false)
  private Boolean ativo;

  @Column(name = "data_criacao", nullable = false, updatable = false)
  private LocalDateTime dataCriacao;

  @PrePersist
  public void prePersist() {
    this.dataCriacao = LocalDateTime.now();
  }

}