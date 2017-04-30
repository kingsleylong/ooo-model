package kiss.infrastructure;

import kiss.domain.user.User;
import kiss.domain.user.UserRepository;
import kiss.infrastructure.mapper.UserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * Created by kiss on 2017/4/16.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserMapper userMapper;

    private Boolean editingMode = Boolean.FALSE;

    private User detailedUserOrigin;

    @Override
    public User find(String userId) {
        User detailedUser = userMapper.getDetailedUser(userId);
        if (editingMode) {
            detailedUserOrigin = new User(null);
//            BeanUtils.copyProperties(detailedUser, detailedUserOrigin);
            detailedUserOrigin = detailedUser.deepClone();
        }
        return detailedUser;
    }

    @Override
    public void store(User user) {
        if (editingMode) {
            if (!user.equals(detailedUserOrigin)) {
                System.out.println("user changed!");
            }
            if (!user.getPhones().equals(detailedUserOrigin.getPhones())) {
                System.out.println("phones changed!");
                if(CollectionUtils.subtract(detailedUserOrigin.getPhones(), user.getPhones()).size() > 0) {
                    System.out.println("phone removal detected!");
                }
                if(CollectionUtils.subtract(user.getPhones(), detailedUserOrigin.getPhones()).size() > 0) {
                    System.out.println("phone addition detected!");
                }
                Collection intersection = CollectionUtils.intersection(detailedUserOrigin.getPhones(), user.getPhones());
                //...
            }
        }
    }

    @Override
    public void add(User user) {
        userMapper.insertUser(user);
        System.out.println(user);
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void useEditingMode() {
        this.editingMode = Boolean.TRUE;
    }
}
