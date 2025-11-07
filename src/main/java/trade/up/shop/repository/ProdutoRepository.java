package trade.up.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.up.shop.model.Produto;

public interface ProdutoRepository extends JpaRepository <Produto, Long> {
    
}
