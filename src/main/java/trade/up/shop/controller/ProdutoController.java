package trade.up.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import trade.up.shop.model.Produto;
import trade.up.shop.service.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String listarProdutos(Model model) {
        model.addAttribute("categorias", produtoService.listarCategorias());
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "produtos";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto, Model model) {
        try {
            produtoService.salvar(produto);
            return "redirect:/produtos";
        } catch (Exception e) {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao salvar produto: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id, Model model) {
        if (produtoService.excluir(id)) {
            return "redirect:/produtos";
        } else {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao excluir produto");
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        return produtoService.buscarPorId(id)
                .map(produto -> {
                    model.addAttribute("produto", produto);
                    model.addAttribute("categorias", produtoService.listarCategorias());
                    return "editarProduto";
                })
                .orElseGet(() -> {
                    model.addAttribute("errorCode", 404);
                    model.addAttribute("errorMessage", "Produto não encontrado");
                    return "error";
                });
    }

    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id, @ModelAttribute Produto produtoAtualizado, Model model) {
        if (produtoService.atualizar(id, produtoAtualizado)) {
            return "redirect:/produtos";
        } else {
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Produto não encontrado");
            return "error";
        }
    }
}
