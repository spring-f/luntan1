package life.majiang.community.community.controller;

import life.majiang.community.community.Mapper.CommentMapper;
import life.majiang.community.community.Mapper.UserMapper;
import life.majiang.community.community.Model.User;
import life.majiang.community.community.dto.PageDTO;
import life.majiang.community.community.enums.NotificationTypeEnum;
import life.majiang.community.community.service.CommentService;
import life.majiang.community.community.service.NotificationService;
import life.majiang.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommentMapper commentMapper;

                    @GetMapping("/profile/{action}")
                    public String profile(HttpServletRequest request,
                            @PathVariable(name = "action") String action,
                            Model model, @RequestParam(name="page",defaultValue = "1") Integer
                    page,@RequestParam(name = "size",defaultValue = "10") Integer size
                          ){
                        User user=(User)request.getSession().getAttribute("user");
                        if (user==null){
                            return "redirect:/";
                        }

                        if ("questions".equals(action)){
                            model.addAttribute("section","questions");
                            model.addAttribute("sectionName","我的提问");
                            PageDTO pageDTO =questionService.list(user.getId(),page,size);
                            model.addAttribute("questionList" ,pageDTO);
                }else if ("repies".equals(action)){
                    model.addAttribute("section","repies");
                    model.addAttribute("sectionName","最新评论");
                            PageDTO pageDTO=notificationService.list(user.getId(),page,size,1);
                            model.addAttribute("questionList2" ,pageDTO);
                            model.addAttribute("totalCount",pageDTO.getTotalCount());
//                            notificationService.updateReadType(user.getId(),1);
                }else if ("answer".equals(action)){
                            model.addAttribute("section","answer");
                            model.addAttribute("sectionName","最新回复");
                            PageDTO pageDTO=notificationService.list(user.getId(),page,size,2);
                            model.addAttribute("questionList3" ,pageDTO);
                            model.addAttribute("c",pageDTO.getTotalCount());
//                            notificationService.updateReadType(user.getId(),2);
                        }

                        int totalCount=commentMapper.countById(user.getId(),1,2);
                        model.addAttribute("totalCount",totalCount);
                        int c=commentMapper.countById(user.getId(),2,2);
                        model.addAttribute("c",c);
                return "profile";
}




}
