package trade.up.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.up.shop.model.Categoria;

public interface CategoriaRepository extends JpaRepository <Categoria, Long> {

}
