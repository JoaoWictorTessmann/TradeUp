package trade.up.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import trade.up.shop.model.Produto;
import trade.up.shop.repository.CategoriaRepository;
import trade.up.shop.repository.ProdutoRepository;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarProdutos(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        return "produtos";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto, Model model) {
        try {
            produtoRepository.save(produto);
            return "redirect:/produtos";
        } catch (Exception e) {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao salvar produto: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id, Model model) {
        try {
            produtoRepository.deleteById(id);
            return "redirect:/produtos";
        } catch (Exception e) {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao excluir produto: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto != null) {
            model.addAttribute("produto", produto);
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "editarProduto";
        } else {
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Produto não encontrado");
            return "error";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id, @ModelAttribute Produto produtoAtualizado,
            Model model) {
        try {
            Produto produtoExistente = produtoRepository.findById(id).orElse(null);
            if (produtoExistente != null) {
                produtoExistente.setNome(produtoAtualizado.getNome());
                produtoExistente.setDescricao(produtoAtualizado.getDescricao());
                produtoExistente.setPreco(produtoAtualizado.getPreco());
                produtoExistente.setCategoria(produtoAtualizado.getCategoria());
                produtoRepository.save(produtoExistente);
                return "redirect:/produtos";
            } else {
                model.addAttribute("errorCode", 404);
                model.addAttribute("errorMessage", "Produto não encontrado");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao atualizar produto: " + e.getMessage());
            return "error";
        }
    }
}
