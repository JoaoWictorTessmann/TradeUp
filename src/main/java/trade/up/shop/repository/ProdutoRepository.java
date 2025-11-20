package trade.up.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import trade.up.shop.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByNome(String nome);

    Optional<Produto> findByNome(String nome);
}
