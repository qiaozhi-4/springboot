import com.zking.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findById();
}
