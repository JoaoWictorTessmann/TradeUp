package trade.up.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalCategorias", 5);
        model.addAttribute("totalProdutos", 12);
        return "home";
    }
}
