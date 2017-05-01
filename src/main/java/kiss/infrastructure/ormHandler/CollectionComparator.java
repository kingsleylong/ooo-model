package kiss.infrastructure.ormHandler;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by kiss on 2017/4/19.
 */
@Slf4j
public class CollectionComparator<T> {
    public static <T> CompareResult<T> compare(Collection<T> left, Collection<T> right) throws IllegalAccessException {
        Collection<T> unmodifiedElements = new HashSet<T>();
        Collection<T> newElements = new HashSet<T>();
        Collection<T> deletedElements = null;
        Collection<CollectionComparator.ModifiedElement<T>> modifiedElements = new HashSet<>();

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
            } else {
                String rightId = readIdProperty(rightEle);
                if (null == rightId) {
                    newElements.add(rightEle);
                } else {
                    T leftEle = lookUpOrigin(left, rightId);
                    modifiedElements.add(new ModifiedElement<T>(rightId, leftEle, rightEle));
                }
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
            for (ModifiedElement m : modifiedElements) {
                if (readIdProperty(next).equals(m.getId())) {
                    iterator.remove();
                }
            }
        }

        return new CompareResult(newElements, deletedElements, modifiedElements);
    }

    private static <T> T lookUpOrigin(Collection<T> left, String rightId) throws IllegalAccessException {
        T origin = null;

        String leftId = null;
        for (T t :
                left) {
            leftId = readIdProperty(t);
            if (rightId.equals(leftId)) {
                origin = t;
            }
        }

        return origin;
    }

    private static <T> String readIdProperty(T rightEle) throws IllegalAccessException {
        String id = null;
        Field idField = AbstractDirtyCheckRepository.findIdField(rightEle.getClass());
        if (null != idField) {
            boolean idFieldAccessible = idField.isAccessible();
            if (!idFieldAccessible) {
                idField.setAccessible(true);
            }
            id = (String) idField.get(rightEle);
            idField.setAccessible(idFieldAccessible);
        }
        return id;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Value
    static class ModifiedElement<E> {
        private String id;
        private E origin;
        private E current;
    }

}
