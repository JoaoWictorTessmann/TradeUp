package trade.up.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
