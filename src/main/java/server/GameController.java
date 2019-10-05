package server;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {


    @GetMapping("/game")
    public String index(Model model) {
        
       return "index";
    }
}