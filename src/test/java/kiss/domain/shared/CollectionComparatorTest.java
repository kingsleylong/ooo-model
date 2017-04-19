package kiss.domain.shared;

import kiss.domain.user.Phone;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by kiss on 2017/4/19.
 */
public class CollectionComparatorTest {

    @Test
    public void testCompare() {
        Phone p0 = new Phone("p0", "000000", "");
        Phone p1 = new Phone("p1", "1212", "");
        Phone p2 = new Phone("p2", "56324412", "");

        Collection<Phone> origin = new HashSet<Phone>();
        origin.add(p0);
        origin.add(p1);
        origin.add(p2);

        Collection<Phone> copy = (Collection<Phone>)UnoptimizedDeepCopy.copy(origin);

        CompareResult compareResult = new CollectionComparator<Phone>().compare(origin, copy);
        System.out.println(compareResult);

        Iterator<Phone> iterator = copy.iterator();
        iterator.next().setBand("New Band");
        iterator.next();
        iterator.remove();
        copy.add(new Phone(null, "264375", "Schecher"));

        CompareResult compareResult2 = new CollectionComparator<Phone>().compare(origin, copy);
        System.out.println(compareResult2);
    }

}