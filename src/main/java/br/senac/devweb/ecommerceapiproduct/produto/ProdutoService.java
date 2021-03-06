package br.senac.devweb.ecommerceapiproduct.produto;

import br.senac.devweb.ecommerceapiproduct.categoria.Categoria;
import br.senac.devweb.ecommerceapiproduct.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto salvar(ProdutoRepresentation.CreateOrUpdate createOrUpdate, Categoria categoria) {
        Produto produto = Produto.builder()
                .nome(createOrUpdate.getNome())
                .descricao(createOrUpdate.getDescricao())
                .complemento(Strings.isEmpty(createOrUpdate.getComplemento()) ? "" : createOrUpdate.getComplemento())
                .fabricante(createOrUpdate.getFabricante())
                .fornecedor(Strings.isEmpty(createOrUpdate.getFornecedor()) ? "" : createOrUpdate.getFornecedor())
                .qtde(createOrUpdate.getQtde())
                .valor(createOrUpdate.getValor())
                .unidadeMedida(createOrUpdate.getUnidadeMedida())
                .status(Produto.Status.ATIVO)
                .categoria(categoria)
                .build();

        return this.produtoRepository.save(produto);
    }

    public Produto update(Long id, ProdutoRepresentation.CreateOrUpdate createOrUpdate, Categoria categoria) {
        Produto produtoAntigo = this.getProduto(id);
        Produto produtoAtualizado = produtoAntigo.toBuilder()
                .nome(createOrUpdate.getNome())
                .descricao(createOrUpdate.getDescricao())
                .complemento(Strings.isEmpty(createOrUpdate.getComplemento()) ? "" : createOrUpdate.getComplemento())
                .fabricante(createOrUpdate.getFabricante())
                .fornecedor(Strings.isEmpty(createOrUpdate.getFornecedor()) ? "" : createOrUpdate.getFornecedor())
                .qtde(createOrUpdate.getQtde())
                .valor(createOrUpdate.getValor())
                .unidadeMedida(createOrUpdate.getUnidadeMedida())
                .categoria(categoria)
                .status(Produto.Status.ATIVO)
                .build();

        return this.produtoRepository.save(produtoAtualizado);
    }

    public Page<Produto> getAllProduto(Predicate predicate, Pageable page) {
        return this.produtoRepository.findAll(predicate,page);
    }

    public Produto getProduto(Long id) {
        BooleanExpression filter =
                QProduto.produto.id.eq(id)
                        .and(QProduto.produto.status.eq(Produto.Status.ATIVO));
        return this.produtoRepository.findOne(filter)
                .orElseThrow(() -> new NotFoundException("Produto n??o encontrada."));
    }

    public void deleteProduto(Long id) {
        Produto produto = this.getProduto(id);
        produto.setStatus(Produto.Status.INATIVO);
        this.produtoRepository.save(produto);
    }

}
