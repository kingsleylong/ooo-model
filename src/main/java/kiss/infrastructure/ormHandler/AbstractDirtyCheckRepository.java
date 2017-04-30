package kiss.infrastructure.ormHandler;

import kiss.domain.shared.CollectionComparator;
import kiss.domain.shared.CompareResult;
import kiss.domain.shared.Entity;
import kiss.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by kiss on 2017/4/23.
 */
@Slf4j
public abstract class AbstractDirtyCheckRepository<T> implements Repository<T> {
    private ThreadLocal<T> entityCopy;

    private Mode mode = Mode.READING;

    @Override
    public void useEditingMode() {
        this.mode = Mode.EDITING;
    }

    Boolean isEditingMode() {
        return this.mode.equals(Mode.EDITING);
    }

    void addEditCopy(T t) {
        this.entityCopy.set(t);
    }

    T getEditCopy() {
        return this.entityCopy.get();
    }

    enum Mode {
        READING,
        EDITING
    }

    <D> void persistSaveOrUpdate(D origin, D current) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        if (null == current) {
            throw new IllegalArgumentException("参数校验异常：current对象为null。");
        }
        if (null == origin) {
            log.debug("新增对象：{}", current);
            // TODO 新增持久化流程
        } else {
            if (origin.equals(current)) {
                log.info("对象无变更：{}", current);
            } else {
                log.info("对象有变更：{}", current);
                Field idField = findIdField(current);
                if (null == idField) {
                    log.error("找不到ID属性,请检查注解：{}", current);
                    throw new PersistenceException("找不到ID属性,请检查注解。");
                }
                cascadeSaveOrUpdate(origin, current);
                updateSelf(origin, current);
            }
        }
    }

    private <D> void cascadeSaveOrUpdate(D origin, D current) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Field[] declaredFields = current.getClass().getDeclaredFields();
        for (Field field :
                declaredFields) {
            OneToMany annotation = field.getAnnotation(OneToMany.class);
            if (null == annotation) {
                continue;
            }
            Collection<D> currentAssoc = (Collection<D>) field.get(current);

            Field originField = origin.getClass().getDeclaredField(field.getName());
            Collection<D> originalAssoc = (Collection<D>) originField.get(origin);

            CompareResult<D> compareResult = CollectionComparator.compare(originalAssoc, currentAssoc);
            if (compareResult.isDirty()) {
                saveOrUpdate(compareResult);
            } else {
                log.info("关联对象没有变更。");
            }
        }
    }

    private <D> void updateSelf(D origin, D current) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = origin.getClass().getMethod("equalTo");
        if (null != method) {
            Boolean invokeResult = (Boolean) method.invoke(origin, current);
            if (invokeResult) {
                log.info("无属性变更：{}", current);
            } else {
                log.info("对象本身属性变更，进行持久化：{}", current);
                // TODO 属性持久化流程
            }
        }
    }

    private <D> void saveOrUpdate(CompareResult<D> compareResult) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Collection<D> elements = compareResult.getNewElements();
        if (CollectionUtils.isNotEmpty(elements)) {
            log.debug("新增对象：{}", elements);
            // TODO 新增持久化流程
        }
        elements = compareResult.getDeletedElements();
        if (CollectionUtils.isNotEmpty(elements)) {
            log.debug("删除对象：{}", elements);
            // TODO 删除持久化流程
        }
        elements = compareResult.getModifiedElements();
        if (CollectionUtils.isNotEmpty(elements)) {
            log.debug("修改对象：{}", compareResult.getModifiedElements());
            for (D domain:
                 elements) {
                // TODO 从Comparator获取原对象
                D originDomain = null;
                // TODO 递归调用，重点测试！！
                persistSaveOrUpdate(originDomain, domain);
            }
        }
    }

    // TODO 重点测试递归！！
    private <D> Field findIdField(D current) {
        if (current.getClass().equals(Object.class)) {
            return null;
        }

        Field[] declaredFields = current.getClass().getDeclaredFields();
        for (Field field :
                declaredFields) {
            Id annotation = field.getAnnotation(Id.class);
            if (null == annotation) {
                return findIdField(current.getClass().getSuperclass());
            } else {
                return field;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        AbstractDirtyCheckRepository<User> repository = new AbstractDirtyCheckRepository<User>(){

        };
        repository.findIdField(new User(""));
        repository.findIdField(new Entity());
    }
}
