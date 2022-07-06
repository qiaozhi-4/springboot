import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zking.entity.JsonUser;
import org.junit.Test;

import java.util.Date;

public class TestJson {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test1() throws JsonProcessingException {
        JsonUser user = new JsonUser();
        user.setName("a");
        user.setBirth(null);
        user.setBirth1(new Date());
        user.setImg_url("http://www");
        user.setAge(11);
        user.setCourses(new String[]{"aa", "bb", "cc"});
        user.setFriend(new JsonUser.Friend(1,"555"));
        String s = objectMapper.writeValueAsString(user);
        System.out.println(s);
    }

    @Test
    public void test2(){
        String s = "{\"name\":\"a\",\"birth\":1657078520348,\"birth1\":\"2022-07-06 11-35-20\",\"img_url\":\"http://www\",\"age\":11,\"courses\":[\"aa\",\"bb\",\"cc\"]}";
    }
}
