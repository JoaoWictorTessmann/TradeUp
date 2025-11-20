package trade.up.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.up.shop.model.Categoria;
import trade.up.shop.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria salvar(Categoria categoria) {
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome!");
        }
        return categoriaRepository.save(categoria);
    }

    public boolean excluir(Long id) {
        try {
            categoriaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public boolean atualizar(Long id, Categoria categoriaAtualizada) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(id);
        if (categoriaExistente.isPresent()) {
            Optional<Categoria> duplicado = categoriaRepository.findByNome(categoriaAtualizada.getNome());
            if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
                throw new IllegalArgumentException("Já existe outra categoria com esse nome!");
            }

            Categoria categoria = categoriaExistente.get();
            categoria.setNome(categoriaAtualizada.getNome());
            categoriaRepository.save(categoria);
            return true;
        }
        return false;
    }
}
