package life.majiang.community.community.controller;

import life.majiang.community.community.dto.CommentCreateDTO;
import life.majiang.community.community.dto.PageDTO;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.service.CommentService;
import life.majiang.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id") Integer id, Model model){
        Integer type=1;
        Integer type2=2;
        PageDTO pageDTO=new PageDTO();
        //累阅读数
        questionService.invView(id);
        QuestionDTO questionDTO =questionService.getById(id);
        List<QuestionDTO> questionDTOS = questionService.getTagsById(questionDTO);
        pageDTO.setQuestions(questionDTOS);
        List<CommentCreateDTO> commentCreateDTOS=questionService.listByQuestionId(id,type);
        List<CommentCreateDTO> commentCreateDTOS2=questionService.listByQuestionId(1,2);
        model.addAttribute("question",questionDTO);
        model.addAttribute("commentCreateDTOS",commentCreateDTOS);
        model.addAttribute("commentCreateDTOS2",commentCreateDTOS2);
        model.addAttribute("pageDTO",pageDTO);
        return "question";
    }
}
