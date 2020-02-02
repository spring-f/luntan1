package life.majiang.community.community.service;

import life.majiang.community.community.Mapper.CommentMapper;
import life.majiang.community.community.Mapper.NodefitierMapper;
import life.majiang.community.community.Model.Comment;
import life.majiang.community.community.Model.NodeFition;
import life.majiang.community.community.Model.Question;
import life.majiang.community.community.dto.NotificationDTO;
import life.majiang.community.community.dto.PageDTO;
import life.majiang.community.community.dto.PingLunDTO;
import life.majiang.community.community.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private NodefitierMapper nodefitierMapper;
    PageDTO<PingLunDTO> pageDTO=new PageDTO<>();

    public PageDTO list(Integer userId, Integer page, Integer size,Integer type) {
        Integer totalCount=null;

        Integer totalPage;
        totalCount=commentMapper.countById(userId,type,2);

        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }

        if (page>totalPage){
            page=totalPage;
        }
        if (page<1){
            page=1;
        }

        pageDTO.setPageination(totalPage,size,page);
        Integer offset=size * (page-1);
        if (offset<0){
            offset=0;
        }
        List<PingLunDTO> pingLunDTOS=commentMapper.pingLunById(userId,offset,size,type,2);
        if (pingLunDTOS==null){
            return pageDTO;
        }
        pageDTO.setQuestions(pingLunDTOS);
        pageDTO.setTotalCount(totalCount);
        return pageDTO;
    }

    public void updateReadType(Integer id,Integer type) {
        commentMapper.updateReadType(id,type,2,1);
    }
}
