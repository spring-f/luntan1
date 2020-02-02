package life.majiang.community.community.service;

import life.majiang.community.community.Mapper.CommentMapper;
import life.majiang.community.community.Mapper.NodefitierMapper;
import life.majiang.community.community.Mapper.QuestionMapper;
import life.majiang.community.community.Model.Comment;
import life.majiang.community.community.Model.NodeFition;
import life.majiang.community.community.Model.Question;
import life.majiang.community.community.enums.NotificationEnum;
import life.majiang.community.community.enums.NotificationTypeEnum;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private NodefitierMapper nodefitierMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public void insert1(Comment entity) {

        if (entity.getParentId()==0||entity.getParentId() ==null){
            //未选中任何问题或评论进行回复
        }
/*        if（entity.getType()==null||!commentTypeEnum.isexist(entity.getType())){
          回复的类型错误或者不存在
          }

          if  （commentTypeEnum.isexist(entity.getType())){
          回复评论

          回复的评论不存在
          }

          返回json信息
*/
         commentMapper.insert(entity);
        Question question=questionMapper.getById(entity.getParentId());
        question.setCommentCount(question.getCommentCount()+1);
        questionMapper.updateCommentCount(question);
        createNotify(entity, question,NotificationEnum.REPLY_QUESTION,NotificationTypeEnum.READ
        );
    }
    @Transactional
    public void insert2(Comment entity) {

        if (entity.getParentId()==0||entity.getParentId() ==null){
            //未选中任何问题或评论进行回复
        }
/*        if（entity.getType()==null||!commentTypeEnum.isexist(entity.getType())){
          回复的类型错误或者不存在
          }

          if  （commentTypeEnum.isexist(entity.getType())){
          回复评论

          回复的评论不存在
          }

          返回json信息
*/
        commentMapper.insert(entity);
        Question question=questionMapper.getById(entity.getParentId());
        question.setCommentCount(question.getCommentCount()+1);
        questionMapper.updateCommentCount(question);

        //创建消息
        createNotify(entity, question,NotificationEnum.REPLY_COMMENT,NotificationTypeEnum.UNREAD);
    }

    private void createNotify(Comment entity, Question question,NotificationEnum notificationEnum,
                              NotificationTypeEnum notificationTypeEnum) {
        NodeFition nodeFition=new NodeFition();
        nodeFition.setGmtCreate(System.currentTimeMillis());
        nodeFition.setNotifier(entity.getCommentator());
        nodeFition.setOuterId(entity.getParentId());
        nodeFition.setType(notificationEnum.getStatus());
        nodeFition.setStatus(notificationTypeEnum.getStatus());
        nodeFition.setReceiver(question.getCreator());
    }
}
