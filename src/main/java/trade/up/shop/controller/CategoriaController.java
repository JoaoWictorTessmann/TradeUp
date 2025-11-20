package trade.up.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import trade.up.shop.model.Categoria;
import trade.up.shop.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("produtos", null);
        return "categorias";
    }

    @PostMapping("/salvar")
    public String salvar(Categoria categoria, Model model) {
        try {
            categoriaService.salvar(categoria);
            return "redirect:/categorias";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorCode", 400);
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        } catch (Exception e) {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao salvar categoria: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable Long id, Model model) {
        if (categoriaService.excluir(id)) {
            return "redirect:/categorias";
        } else {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao excluir categoria");
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        return categoriaService.buscarPorId(id)
                .map(categoria -> {
                    model.addAttribute("categoria", categoria);
                    return "editarCategoria";
                })
                .orElseGet(() -> {
                    model.addAttribute("errorCode", 404);
                    model.addAttribute("errorMessage", "Categoria não encontrada");
                    return "error";
                });
    }

    @PostMapping("/editar/{id}")
    public String atualizarCategoria(@PathVariable Long id, Categoria categoriaAtualizada, Model model) {
        try {
            if (categoriaService.atualizar(id, categoriaAtualizada)) {
                return "redirect:/categorias";
            } else {
                model.addAttribute("errorCode", 404);
                model.addAttribute("errorMessage", "Categoria não encontrada");
                return "error";
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorCode", 400);
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        } catch (Exception e) {
            model.addAttribute("errorCode", 500);
            model.addAttribute("errorMessage", "Erro ao atualizar categoria: " + e.getMessage());
            return "error";
        }
    }
}
