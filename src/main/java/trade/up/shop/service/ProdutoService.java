package trade.up.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.up.shop.model.Produto;
import trade.up.shop.repository.ProdutoRepository;
import trade.up.shop.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public List<?> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Produto salvar(Produto produto) {
        Optional<Produto> existente = produtoRepository.findByNome(produto.getNome());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Já existe um produto com esse nome!");
        }
        return produtoRepository.save(produto);
    }

    public boolean excluir(Long id) {
        try {
            produtoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

     public boolean atualizar(Long id, Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();

            Optional<Produto> duplicado = produtoRepository.findByNome(produtoAtualizado.getNome());
            if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
                throw new IllegalArgumentException("Já existe outro produto com esse nome!");
            }

            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setCategoria(produtoAtualizado.getCategoria());
            produtoRepository.save(produto);
            return true;
        }
        return false;
    }
}
