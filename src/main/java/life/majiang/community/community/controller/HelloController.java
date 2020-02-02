package life.majiang.community.community.controller;
import life.majiang.community.community.dto.PageDTO;
import life.majiang.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class HelloController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search
    ){
        PageDTO pageDTO=questionService.list(search,page,size);
        model.addAttribute("questionList" ,pageDTO);
        model.addAttribute("search",search);
        return "index";
    }
}