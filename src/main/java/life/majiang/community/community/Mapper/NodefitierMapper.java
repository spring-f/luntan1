package life.majiang.community.community.Mapper;

import life.majiang.community.community.Model.NodeFition;
import life.majiang.community.community.Model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodefitierMapper {

    @Select("select count(1) from notifier where receiver=${userId}")
    Integer countById(@Param(value = "userId")Integer userId);

    @Select("select * from notifier where receiver=#{userId} limit #{offset},#{size}")
    List<NodeFition> listd(Integer userId, Integer offset, Integer size);
}
