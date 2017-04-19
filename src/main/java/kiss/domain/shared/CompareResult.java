package kiss.domain.shared;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by kiss on 2017/4/19.
 */
public class CompareResult<T extends Entity> {
    private Collection<T> newElements;
    private Collection<T> deletedElements;
    private Collection<T> modifiedElements;

    public CompareResult(Collection<T> newElements, Collection<T> deletedElements, Collection<T> modifiedElements) {
        this.newElements = newElements;
        this.deletedElements = deletedElements;
        this.modifiedElements = modifiedElements;
    }

    @Override
    public String toString() {
        return "CompareResult{" +
                "newElements=" + newElements +
                ", deletedElements=" + deletedElements +
                ", modifiedElements=" + modifiedElements +
                '}';
    }
}
