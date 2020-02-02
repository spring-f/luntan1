package life.majiang.community.community.Mapper;
import life.majiang.community.community.Model.Comment;
import life.majiang.community.community.dto.PingLunDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface CommentMapper{

    @Insert("insert into comment(parentId,type,commentator,gmt_create,gmt_modified,like_count,content) values (" +
            "#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where parentId=#{id} and type=#{type} order by gmt_create desc")
    List<Comment> selectByParentId(@Param("id") Integer id, @Param("type") Integer type);

    @Select("select count(1) from comment a left join (select id from question WHERE creator=#{userId}) q on q.id=a" +
            ".parentId where a.type=#{type} and readType=#{readType}")

    Integer countById(@Param("userId") Integer userId,@Param("type") Integer type,@Param("readType") Integer readType);

    @Select("SELECT q.title,a.gmt_modified,u.name from comment a left join \n" +
            "(select title,id from question WHERE creator=#{userId}) q \n" +
            "on q.id=a.parentId \n" +
            "left join user u on a.commentator=u.id\n" +
            "WHERE a.type=#{type} and readType=#{readType} order by a.gmt_modified desc LIMIT #{offset},#{size}")
    List<PingLunDTO> pingLunById(@Param("userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value =
            "size") Integer size, @Param("type") Integer type,@Param("readType") Integer readType);
    @Update("update comment set readType=#{read} where id in\n" +
            "(select * from\n" +
            "(SELECT a.id from comment a left join \n" +
            "(select title,id from question WHERE creator=#{userId}) q on q.id=a.parentId \n" +
            " WHERE a.type=#{type} and a.readType=#{readType}) as temp)")
    void updateReadType(@Param("userId") Integer userId,@Param("type") Integer type,
                        @Param("readType") Integer readType,@Param("read") Integer read);
}
