package com.github.gigiosouza.springdemo.controller;

import com.github.gigiosouza.springdemo.models.Produto;
import com.github.gigiosouza.springdemo.repository.ProdutoRepository;
import com.github.gigiosouza.springdemo.views.ProdutoFormView;
import com.github.gigiosouza.springdemo.views.ProdutoListView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutosController {

  private final ProdutoRepository repository;

  @PostMapping
  public @ResponseBody
  ResponseEntity salvar(@RequestBody @Valid ProdutoFormView produtoFormView) {
    Produto produto = produtoFormView.toEntity();

    try {
      produto = this.repository.save(produto);
      return ResponseEntity.ok(produto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deu ruim champs");
    }
  }


  @GetMapping("/{id}")
  public @ResponseBody
  ResponseEntity retornarPeloId(@PathVariable Long id) {
    Optional<Produto> optionalProduto = this.repository.findById(id);

    if (optionalProduto.isPresent()) {
      return ResponseEntity.ok(ProdutoFormView.fromEntity(optionalProduto.get()));
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deu ruim champs, procurou o cara errado");
  }

  @GetMapping
  @Transactional
  public @ResponseBody
  ResponseEntity paginar(
    @PageableDefault(size = 5) Pageable pageRequest,
    @RequestParam(required = false) String nome,
    @RequestParam(required = false) String categoria
  ) {
    Page<Produto> produtos = null;
    if (nome != null || categoria != null) {
      Specification<Produto> specifications = (root, query, cb) -> {
        Collection<Predicate> predicates = new ArrayList<>();

        if (categoria != null && !categoria.isEmpty()) {
          predicates.add(
            cb.like(cb.upper(root.get("categoria")), "%" + categoria.toUpperCase() + "%")
          );
        }

        if (nome != null && !nome.isEmpty()) {
          predicates.add(
            cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%")
          );
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      };

      produtos = this.repository.findAll(specifications, pageRequest);
    } else {
      produtos = this.repository.findAll(pageRequest);
    }

    if (produtos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(produtos);
  }

}
