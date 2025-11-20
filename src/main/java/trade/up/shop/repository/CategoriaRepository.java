package trade.up.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import trade.up.shop.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNome(String nome);

    Optional<Categoria> findByNome(String nome);
}
