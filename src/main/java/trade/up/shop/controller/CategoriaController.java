package trade.up.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import trade.up.shop.model.Categoria;
import trade.up.shop.repository.CategoriaRepository;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("produtos", null);
        return "categorias";
    }

    @PostMapping("/salvar")
    public String salvar(Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id, Model model) {
        try {
            categoriaRepository.deleteById(id);
            return "redirect:/categorias";
        } catch (Exception e) {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao excluir categoria: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria != null) {
            model.addAttribute("categoria", categoria);
            return "editarCategoria";
        } else {
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", "Categoria não encontrada");
            return "error";
        }
    }

    @PostMapping("/editar/{id}")
    public String atualizarCategoria(@PathVariable Long id, Categoria categoriaAtualizada, Model model) {
        try {
            Categoria categoriaExistente = categoriaRepository.findById(id).orElse(null);
            if (categoriaExistente != null) {
                categoriaExistente.setNome(categoriaAtualizada.getNome());
                categoriaRepository.save(categoriaExistente);
                return "redirect:/categorias";
            } else {
                model.addAttribute("errorCode", 404);
                model.addAttribute("errorMessage", "Categoria não encontrada");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao atualizar categoria: " + e.getMessage());
            return "error";
        }
    }

}