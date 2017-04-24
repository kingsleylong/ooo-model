package kiss.domain.shared.jpatest;

import kiss.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by kiss on 2017/4/23.
 */
@RunWith(JUnit4.class)
public class UserTest {

    @Test
    public void testJpaAnnotation() {
        User user = new User("1", "Tom");
        Entity annotation = user.getClass().getAnnotation(Entity.class);
        System.out.println(annotation);

        System.out.println(user.getClass().getDeclaredAnnotations());

        Field[] declaredFields = user.getClass().getDeclaredFields();
        for (Field field:
             declaredFields) {
            Annotation[] annotations = field.getAnnotations();
            System.out.println(annotations);
            for (Annotation a:
                 annotations) {
                System.out.println(a.toString());

                if (a.annotationType().equals(Id.class)) {
                    System.out.println("An ID Property");
                }
            }
//            System.out.println(field.getName() + annotations[0].annotationType());

            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
//            System.out.println(field.getName() + declaredAnnotations[0].annotationType());
        }
    }
}