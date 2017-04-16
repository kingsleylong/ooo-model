package kiss;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

/**
 * Created by kiss on 2017/4/16.
 */
public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(new FileInputStream(""));
        SqlSession sqlSession = factory.openSession();
        sqlSession.update("", new Object());
        Collection<ResultMap> resultMaps = sqlSession.getConfiguration().getResultMaps();
        resultMaps.iterator().next().getResultMappings().get(0).isCompositeResult();
    }
}
