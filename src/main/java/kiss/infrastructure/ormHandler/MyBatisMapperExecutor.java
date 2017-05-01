package kiss.infrastructure.ormHandler;

import kiss.infrastructure.mapper.UserMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kiss on 2017/5/1.
 */
public class MyBatisMapperExecutor extends SqlSessionDaoSupport {
    @Autowired
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    public static <T> void insert(T domain) {
//        loadMapper(domain.getClass());
//        invokeMapper(Action.INSERT, new UserMapper());
    }

    private Object loadMapper(Class<?> domainClass) {
//        this.getSqlSession().select
        return null;
    }

    enum Action {
        INSERT,
        UPDATE,
        DELETE
    }
}
