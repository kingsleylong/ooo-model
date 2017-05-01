package kiss.infrastructure.ormHandler;

import kiss.domain.user.User;
import kiss.infrastructure.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kiss on 2017/5/1.
 */
@Slf4j
public abstract class MyBatisDirtyCheckRepository<T> extends AbstractDirtyCheckRepository<T> {
    @Autowired
    private UserMapper userMapper;

    @Override
    protected <D> void insert(D domain) {
        if (domain.getClass().equals(User.class)) {
            log.info("INSERT:{}", domain);
            userMapper.insertUser((User) domain);
        }
    }

    @Override
    protected <D> void update(D domain) {
        if (domain.getClass().equals(User.class)) {
            log.info("UPDATE:{}", domain);
//            userMapper.update((User) domain);
        }
    }
}
