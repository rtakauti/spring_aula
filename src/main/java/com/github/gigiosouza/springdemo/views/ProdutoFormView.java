package com.github.gigiosouza.springdemo.views;

import com.github.gigiosouza.springdemo.models.Produto;
import com.github.gigiosouza.springdemo.validations.tags.Alterar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoFormView {

  private Long id;

  @NotNull
  private String nome;

  @NotNull
  private String categoria;

  @NotNull(groups = { Alterar.class })
  private Boolean ativo;

  private LocalDateTime dataCriacao;

  public static ProdutoFormView fromEntity(Produto produto) {
    return new ProdutoFormView(
      produto.getId(),
      produto.getNome(),
      produto.getCategoria(),
      produto.getAtivo(),
      produto.getDataCriacao()
    );
  }

  public Produto toEntity() {
    return new Produto(
      this.id,
      this.nome,
      this.categoria,
      this.ativo,
      this.dataCriacao
    );
  }

}
