package kiss.domain.shared;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kiss on 2017/4/19.
 */
public class CollectionComparator<T extends Entity> {
    public CompareResult compare(Collection<T> left, Collection<T> right) {
        Collection<T> unmodifiedElements = new HashSet<T>();
        Collection<T> newElements = new HashSet<T>();
        Collection<T> deletedElements = null;
        Collection<T> modifiedElements = new HashSet<T>();

        for (T rightEle : right) {
            if (null == rightEle) continue;
            if (left.contains(rightEle)) {
                unmodifiedElements.add(rightEle);
            } else if (null == rightEle.getId()) {
                newElements.add(rightEle);
            } else {
                modifiedElements.add(rightEle);
            }
        }

        System.out.println("Unmodified Elements:" + unmodifiedElements);

        deletedElements = CollectionUtils.subtract(left, unmodifiedElements);
        deletedElements = CollectionUtils.subtract(deletedElements, newElements);

        Iterator<T> iterator = deletedElements.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            for (T m : modifiedElements) {
                if (next.getId().equals(m.getId())) {
                    iterator.remove();
                }
            }
        }

        return new CompareResult(newElements, deletedElements, modifiedElements);
    }
}
