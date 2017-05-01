package kiss.domain.shared;

import kiss.domain.user.Phone;
import kiss.infrastructure.ormHandler.CollectionComparator;
import kiss.infrastructure.ormHandler.CompareResult;
import kiss.infrastructure.ormHandler.UnoptimizedDeepCopy;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by kiss on 2017/4/19.
 */
public class CollectionComparatorTest {

    @Test
    public void testCompare() throws IllegalAccessException {
        Phone p0 = new Phone("p0", "000000", "", null);
        Phone p1 = new Phone("p1", "1212", "", null);
        Phone p2 = new Phone("p2", "56324412", "", null);

        Collection<Phone> origin = new HashSet<Phone>();
        origin.add(p0);
        origin.add(p1);
        origin.add(p2);

        Collection<Phone> copy = (Collection<Phone>) UnoptimizedDeepCopy.copy(origin);

        CompareResult compareResult = new CollectionComparator<Phone>().compare(origin, copy);
        System.out.println(compareResult);

        Iterator<Phone> iterator = copy.iterator();
        iterator.next().setBand("New Band");
        iterator.next();
        iterator.remove();
        copy.add(new Phone(null, "264375", "Schecher", null));

        CompareResult compareResult2 = new CollectionComparator<Phone>().compare(origin, copy);
        System.out.println(compareResult2);
    }

}