package kiss.infrastructure.ormHandler;

import kiss.domain.shared.CompareResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kiss on 2017/4/19.
 */
@Slf4j
public class CollectionComparator<T> {
    public static <T> CompareResult compare(Collection<T> left, Collection<T> right) throws IllegalAccessException {
        Collection<T> unmodifiedElements = new HashSet<T>();
        Collection<T> newElements = new HashSet<T>();
        Collection<T> deletedElements = null;
        Collection<T> modifiedElements = new HashSet<T>();

        if (null == left) {
            newElements = right;
            return new CompareResult(newElements, null, null);
        }
        if (null == right) {
            deletedElements = left;
            return new CompareResult(null, deletedElements, null);
        }
        for (T rightEle : right) {
            if (null == rightEle) continue;
            if (left.contains(rightEle)) {
                unmodifiedElements.add(rightEle);
            } else if (null == readIdProperty(rightEle)) {
                newElements.add(rightEle);
            } else {
                modifiedElements.add(rightEle);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("未变更的元素：{}", unmodifiedElements);
        }

        deletedElements = CollectionUtils.subtract(left, unmodifiedElements);
        deletedElements = CollectionUtils.subtract(deletedElements, newElements);

        Iterator<T> iterator = deletedElements.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            for (T m : modifiedElements) {
                if (readIdProperty(next).equals(readIdProperty(m))) {
                    iterator.remove();
                }
            }
        }

        return new CompareResult(newElements, deletedElements, modifiedElements);
    }

    private static <T> String readIdProperty(T rightEle) throws IllegalAccessException {
        String id = null;
        Field idField = AbstractDirtyCheckRepository.findIdField(rightEle.getClass());
        if (null != idField) {
            id = (String) idField.get(rightEle);
        }
        return id;
    }
}
