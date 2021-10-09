package br.senac.devweb.ecommerceapiproduct.produto;

import br.senac.devweb.ecommerceapiproduct.categoria.CategoriaRepresentation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface ProdutoRepresentation {

    @Data
    @Getter
    @Setter
    class CreateOrUpdate {

        @NotNull(message = "O campo nome não pode ser nulo")
        @Size(min = 1, max = 30, message = "O campo nome deve ter entre 1 a 30 caracteres")
        private String nome;

        @NotNull(message = "O campo descrição não pode ser nulo")
        @Size(min = 1, max = 255, message = "O campo descrição deve ter entre 1 a 255 caracteres")
        private String descricao;

        private String complemento;

        @NotNull(message = "O campo valor não pode ser nulo")
        private Double valor;

        @NotNull(message = "O campo unidade de medida não pode ser nulo")
        private Produto.UnidadeMedida unidadeMedida;

        @NotNull(message = "O campo quantidade não pode ser nulo")
        private Double qtde;

        @NotNull(message = "O campo fabricante não pode ser nulo")
        @Size(min = 1, max = 255, message = "O campo fabricante deve ter entre 1 a 255 caracteres")
        private String fabricante;

        private String fornecedor;

        @NotNull(message = "A categoria é obrigatória")
        private Long categoria;

    }

    @Data
    @Getter
    @Setter
    @Builder
    class Detail {
        private Long id;
        private String nome;
        private String descricao;
        private String complemento;
        private Double valor;
        private Produto.UnidadeMedida unidadeMedida;
        private Double qtde;
        private String fabricante;
        private String fornecedor;
        private CategoriaRepresentation.Detail categoria;


        public static Detail from(Produto produto) {
            return Detail.builder()
                    .nome(produto.getNome())
                    .descricao(produto.getDescricao())
                    .complemento(produto.getComplemento())
                    .valor(produto.getValor())
                    .qtde(produto.getQtde())
                    .unidadeMedida(produto.getUnidadeMedida())
                    .fabricante(produto.getFabricante())
                    .fornecedor(produto.getFornecedor())
                    .categoria(CategoriaRepresentation.Detail.from(produto.getCategoria()))
                    .build();

        }


    }

    @Data
    @Getter
    @Setter
    @Builder
    class Lista {
        private Long id;
        private String nome;
        private String descricao;
        private Double valor;
        private Produto.UnidadeMedida unidadeMedida;
        private Double qtde;

        private static Lista from(Produto produto) {
            return Lista.builder()
                    .id(produto.getId())
                    .nome(produto.getNome())
                    .descricao(produto.getDescricao())
                    .valor(produto.getValor())
                    .unidadeMedida(produto.getUnidadeMedida())
                    .qtde(produto.getQtde())
                    .build();
        }

        public static List<Lista> from(List<Produto> produtos) {
            return produtos.stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
