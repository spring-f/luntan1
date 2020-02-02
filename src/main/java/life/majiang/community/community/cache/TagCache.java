package life.majiang.community.community.cache;

import life.majiang.community.community.dto.TagDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class TagCache {
    public List<TagDTO> get(){
        List<TagDTO> tagDTOS=new ArrayList<>();
        TagDTO aac=new TagDTO();
        aac.setCategoryName("开发语言");
        aac.setTags(Arrays.asList("js","html","css","java","node.js"));
        tagDTOS.add(aac);

        TagDTO abc=new TagDTO();
        abc.setCategoryName("平台框架");
        abc.setTags(Arrays.asList("spring","yii","mybatis"));
        tagDTOS.add(abc);
        return tagDTOS;
    }
}
