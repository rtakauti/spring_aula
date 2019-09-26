package com.github.gigiosouza.springdemo.views;

import com.github.gigiosouza.springdemo.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoListView {

  private Long id;
  private String nome;
  private String categoria;
  private Boolean ativo;
  private LocalDate dataCriacao;

  public static ProdutoListView fromEntity(Produto produto) {
    return new ProdutoListView(
      produto.getId(),
      produto.getNome(),
      produto.getCategoria(),
      produto.getAtivo(),
      produto.getDataCriacao().toLocalDate()
    );
  }

}
