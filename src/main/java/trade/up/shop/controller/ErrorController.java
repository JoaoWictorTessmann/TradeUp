package trade.up.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int statusCode = 0;
        String mensagem = "Algo inesperado aconteceu. Mas fique tranquilo, já estamos cuidando disso!";

        if (status != null) {
            statusCode = Integer.parseInt(status.toString());

            switch (statusCode) {
                case 404:
                    mensagem = "Ops! Não encontramos a página que você procurava.";
                    break;
                case 403:
                    mensagem = "Você não tem permissão para acessar esta página.";
                    break;
                case 500:
                    mensagem = "Tivemos um probleminha técnico. Já estamos resolvendo!";
                    break;
                default:
                    mensagem = "Algo deu errado. Tente novamente em instantes.";
            }
        }

        model.addAttribute("errorCode", statusCode);
        model.addAttribute("errorMessage", mensagem);

        return "error";
    }
}
