package life.majiang.community.community.controller;

import life.majiang.community.community.Mapper.CommentMapper;
import life.majiang.community.community.Model.Comment;
import life.majiang.community.community.Model.User;
import life.majiang.community.community.dto.CommentDTO;
import life.majiang.community.community.dto.ResultDTO;
import life.majiang.community.community.service.CommentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    Map<Object,Object> objectObjectMap =new HashMap<>();


    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
//            return ResultDTO.errorOf(2002,"未登录，请先登录");
            objectObjectMap.put("message", "未登录，请先登录");
            return objectObjectMap;
        } else if (commentDTO.getContent()==null||commentDTO.getContent()==""){
            objectObjectMap.put("message","回复不能为空");
            return objectObjectMap;
        } else{
            Comment entity = new Comment();
            entity.setParentId(commentDTO.getParentId());
            entity.setContent(commentDTO.getContent());
            entity.setType(commentDTO.getType());
            entity.setGmtModified(System.currentTimeMillis());
            entity.setGmtCreate(System.currentTimeMillis());
            entity.setCommentator(user.getId());
            if (entity.getType()==1) {
                commentService.insert1(entity);
            }else {
                commentService.insert2(entity);
            }
            objectObjectMap.put("message", "成功");
            return objectObjectMap;
        }
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public Object comments(@RequestBody CommentDTO commentDTO,
                              @PathVariable("id") Integer id,
                              HttpServletRequest request){
        commentDTO.setParentId(1);
        commentDTO.setContent("123456");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
//            return ResultDTO.errorOf(2002,"未登录，请先登录");
//            objectObjectMap.put("message", "未登录，请先登录");
//            return objectObjectMap;
        } else if (commentDTO.getContent()==null||commentDTO.getContent()==""){
            objectObjectMap.put("message","回复不能为空");
            return objectObjectMap;
        } else{
            Comment entity = new Comment();
            entity.setParentId(commentDTO.getParentId());
            entity.setContent(commentDTO.getContent());
            entity.setType(commentDTO.getType());
            entity.setGmtModified(System.currentTimeMillis());
            entity.setGmtCreate(System.currentTimeMillis());
            entity.setCommentator(user.getId());
            commentService.insert1(entity);
            objectObjectMap.put("message", "成功");
            return objectObjectMap;
        }
        return objectObjectMap;
    }


}
