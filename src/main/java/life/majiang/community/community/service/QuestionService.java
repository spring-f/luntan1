package life.majiang.community.community.service;
import life.majiang.community.community.Mapper.CommentMapper;
import life.majiang.community.community.Mapper.QuestionMapper;
import life.majiang.community.community.Mapper.UserMapper;
import life.majiang.community.community.Model.Comment;
import life.majiang.community.community.Model.Question;
import life.majiang.community.community.Model.User;
import life.majiang.community.community.dto.CommentCreateDTO;
import life.majiang.community.community.dto.PageDTO;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.dto.QuestionQueryDTO;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class QuestionService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    public  PageDTO list(String search,Integer page, Integer size) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(search)) {
            String[] strings = org.apache.commons.lang3.StringUtils.split(search, " ");
            search = Arrays.stream(strings).collect(Collectors.joining("|"));
            PageDTO<QuestionDTO> pageDTO = new PageDTO<>();
            Integer totalPage;
            QuestionQueryDTO questionQueryDTO=new QuestionQueryDTO();
            questionQueryDTO.setSearch(search);
            Integer totalCount = questionMapper.countBySearch(questionQueryDTO);
            if (totalCount % size == 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }

            if (page < 1) {
                page = 1;
            }
            if (page > totalPage) {
                page = totalPage;
            }
            pageDTO.setPageination(totalPage, size, page);
            Integer offset = size * (page - 1);
            if (offset<0){
                offset=1;
            }
            questionQueryDTO.setPage(offset);
            questionQueryDTO.setSize(size);
            List<Question> questions = questionMapper.selectBySearch(questionQueryDTO);
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            for (Question question : questions) {
                User user = userMapper.findById(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            pageDTO.setQuestions(questionDTOList);
            return pageDTO;
        } else {
            //search==null正常查询
            PageDTO<QuestionDTO> pageDTO = new PageDTO<>();
            Integer totalPage;
            Integer totalCount = questionMapper.count();

            if (totalCount % size == 0) {
                totalPage = totalCount / size;
            } else {
                totalPage = totalCount / size + 1;
            }

            if (page < 1) {
                page = 1;
            }
            if (page > totalPage) {
                page = totalPage;
            }
            pageDTO.setPageination(totalPage, size, page);
            Integer offset = size * (page - 1);
            List<Question> questions = questionMapper.list(offset, size);
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            for (Question question : questions) {
                User user = userMapper.findById(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            pageDTO.setQuestions(questionDTOList);
            return pageDTO;
        }
    }



    public PageDTO list(Integer userId,Integer page, Integer size) {
        PageDTO<QuestionDTO> pageDTO=new PageDTO<>();
        Integer totalPage;
        Integer totalCount=questionMapper.countById(userId);

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
        List<Question> questions=questionMapper.listd(userId,offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question:questions){
            User user =userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user =userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question){
        if (question.getId()==null){
            //创建发布问题
            questionMapper.create(question);
        }else {
            //更新
            questionMapper.update(question);

        }
    }

    public void invView(Integer id) {
        Question question1=questionMapper.getById(id);
        if (question1==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        Integer a=question1.getViewCount()+1;
        question1.setViewCount(a);
        questionMapper.updateViewCount(question1);
    }

    public List<CommentCreateDTO> listByQuestionId(Integer id,Integer type) {
        List<CommentCreateDTO> commentCreateDTOS=new
                ArrayList<>();
        List<Comment> comments=commentMapper.selectByParentId(id,type);
        if (comments==null){
            return new ArrayList<>();
        }
        for (Comment comment:comments){
            CommentCreateDTO commentCreateDTO=new CommentCreateDTO();
            User user=userMapper.findById(comment.getCommentator());
            BeanUtils.copyProperties(comment,commentCreateDTO);
            commentCreateDTO.setUser(user);
            commentCreateDTOS.add(commentCreateDTO);
        }
        return commentCreateDTOS;
    }

    public List<QuestionDTO> getTagsById(QuestionDTO questionDTO) {
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        String[] tags=null;
        if (questionDTO==null||questionDTO.getTag()==null||questionDTO.getTag()==""){
            return new ArrayList<>();
        }
        if (questionDTO.getTag().indexOf(",")==-1) {
             tags = StringUtils.split(questionDTO.getTag(), "，");
        }else if (questionDTO.getTag().indexOf("，")==-1){
             tags = StringUtils.split(questionDTO.getTag(), ",");
        }
            String regexp = Arrays.stream(tags).collect(Collectors.joining("|"));
            Question question = new Question();
            question.setId(questionDTO.getId());
            question.setTag(regexp);
            List<Question> questions = questionMapper.selectByTag(question);
            for (Question question1 : questions) {
                QuestionDTO questionDTO1 = new QuestionDTO();
                User user = userMapper.findById(question1.getCreator());
                BeanUtils.copyProperties(question1, questionDTO1);
                questionDTO1.setUser(user);
                questionDTOS.add(questionDTO1);
            }
            return questionDTOS;
    }
}
