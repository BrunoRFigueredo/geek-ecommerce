package br.senac.devweb.ecommerceapiproduct.produto;

import br.senac.devweb.ecommerceapiproduct.categoria.Categoria;
import br.senac.devweb.ecommerceapiproduct.categoria.CategoriaService;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<ProdutoRepresentation.Detail> cadastrarProduto(@Valid @RequestBody ProdutoRepresentation.CreateOrUpdate createOrUpdate) {
        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoRepresentation.Detail.from(this.produtoService.salvar(createOrUpdate,categoria)));
    }


    @GetMapping
    public ResponseEntity<List<ProdutoRepresentation.Lista>> getAllProduto() {

        BooleanExpression filter = QProduto.produto.status.eq(Produto.Status.ATIVO);

        return ResponseEntity.ok(ProdutoRepresentation.Lista
                .from(this.produtoService.getAllProduto(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detail> getOneProduto(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ProdutoRepresentation.Detail.from(this.produtoService.getProduto(id)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detail> atualizaProduto(@PathVariable("id") Long id,
                                                                        @Valid @RequestBody ProdutoRepresentation.CreateOrUpdate createOrUpdate) {

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProdutoRepresentation.Detail.from(this.produtoService.update(id, createOrUpdate, categoria)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduto(@PathVariable("id") Long id) {
        this.produtoService.deleteProduto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
