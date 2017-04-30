package kiss.domain.shared;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

/**
 * Created by kiss on 2017/4/19.
 */
@Value
@AllArgsConstructor
@ToString
public class CompareResult<T> {
    private Collection<T> newElements;
    private Collection<T> deletedElements;
    private Collection<T> modifiedElements;

    public Boolean isDirty() {
        return CollectionUtils.isNotEmpty(newElements)
                || CollectionUtils.isNotEmpty(deletedElements)
                || CollectionUtils.isNotEmpty(modifiedElements);
    }
}
