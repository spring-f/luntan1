package life.majiang.community.community.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import life.majiang.community.community.Model.Question;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.dto.QuestionQueryDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title}," +
            "#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question order by gmt_create desc limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> listd(  @Param(value = "userId")Integer userId,@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();
    @Select("select count(1) from question where creator=${userId}")
    Integer countById(@Param(value = "userId")Integer userId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified}," +
            "creator=#{creator},tag=#{tag} where id=#{id}")
    void update(Question question);

    @Update("update question set view_count=#{viewCount} where id=#{id}")
    void updateViewCount(Question question);

    @Update("update question set comment_count=#{commentCount} where id=#{id}")
    void updateCommentCount(Question question);

    @Select("SELECT * FROM question where id !=#{id} and tag REGEXP #{tag}")
    List<Question> selectByTag(Question question);

    @Select("select count(*) from question where title REGEXP #{search}")
    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    @Select("select * from question where title REGEXP #{search} order by gmt_create desc limit #{page},#{size}")
    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
