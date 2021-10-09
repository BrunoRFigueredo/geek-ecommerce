package br.senac.devweb.ecommerceapiproduct.produto;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutoRepository extends CrudRepository<Produto, Long>,
        QuerydslPredicateExecutor<Produto> {

    Page<Produto> findAll(Predicate predicate, Pageable page);
}
