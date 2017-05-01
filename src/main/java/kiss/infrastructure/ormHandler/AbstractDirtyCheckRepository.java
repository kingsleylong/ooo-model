package kiss.infrastructure.ormHandler;

import kiss.domain.shared.Entity;
import kiss.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kiss on 2017/4/23.
 */
@Slf4j
public abstract class AbstractDirtyCheckRepository<T> implements Repository<T> {
    private final ThreadLocal<T> entityCopy = new ThreadLocal<>();

    private Mode mode = Mode.READING;

    @Override
    public void useEditingMode() {
        this.mode = Mode.EDITING;
        log.info("进入Editing Mode, {}", Thread.currentThread());
    }

    protected Boolean isEditingMode() {
        return this.mode.equals(Mode.EDITING);
    }

    protected void addEditCopy(T t) {
        this.entityCopy.set((T) UnoptimizedDeepCopy.copy(t));
    }

    protected T getEditCopy() {
        return this.entityCopy.get();
    }

    private enum Mode {
        READING,
        EDITING
    }

    protected <D> void persistSaveOrUpdate(D origin, D current) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        if (null == current) {
            throw new IllegalArgumentException("参数校验异常：current对象为null。");
        }
        if (null == origin) {
            log.info("新增对象，进行持久化：{}", current);
            persistSelf(current);
        } else {
            if (origin.equals(current)) {
                log.info("对象无变更：{}", current);
            } else {
                log.info("对象有变更：{}", current);
                Field idField = findIdField(current.getClass());
                if (null == idField) {
                    log.error("找不到ID属性,请检查注解：{}", current);
                    throw new PersistenceException("找不到ID属性,请检查注解。");
                }
                updateSelf(origin, current);
                cascadeSaveOrUpdate(origin, current);
            }
        }
    }

    private <D> void persistSelf(D domain) throws IllegalAccessException {
        insert(domain);

        List<Field> associationFields = findFieldByAnnotation(domain.getClass(), OneToMany.class);
        if (CollectionUtils.isNotEmpty(associationFields)) {
            for (Field field :
                    associationFields) {
                Collection associations = (Collection) getFieldValue(domain, field);
                for (Object assoc :
                        associations) {
                    persistSelf(assoc);
                }
            }
        }
    }

    public  <D> Object getFieldValue(D domain, Field field) throws IllegalAccessException {
        boolean fieldAccessible = field.isAccessible();
        if (!fieldAccessible) {
            field.setAccessible(true);
        }
        Object result = field.get(domain);

        field.setAccessible(fieldAccessible);
        return result;
    }

    private <D> void cascadeSaveOrUpdate(D origin, D current) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Field[] declaredFields = current.getClass().getDeclaredFields();
        for (Field field :
                declaredFields) {
            OneToMany annotation = field.getAnnotation(OneToMany.class);
            if (null == annotation) {
                continue;
            }
            boolean fieldAccessibleOrigin = field.isAccessible();
            if (!fieldAccessibleOrigin) {
                field.setAccessible(true);
            }
            Collection<D> currentAssoc = (Collection<D>) field.get(current);
            field.setAccessible(fieldAccessibleOrigin);

            Field originField = origin.getClass().getDeclaredField(field.getName());
            fieldAccessibleOrigin = originField.isAccessible();
            if (!fieldAccessibleOrigin) {
                originField.setAccessible(true);
            }
            Collection<D> originalAssoc = (Collection<D>) originField.get(origin);
            originField.setAccessible(fieldAccessibleOrigin);

            CompareResult<D> compareResult = CollectionComparator.compare(originalAssoc, currentAssoc);
            if (compareResult.isDirty()) {
                saveOrUpdate(compareResult);
            } else {
                log.info("关联对象没有变更。");
            }
        }
    }

    private <D> void updateSelf(D origin, D current) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = origin.getClass().getMethod("equalTo", Object.class);
        if (null != method) {
            Boolean invokeResult = (Boolean) method.invoke(origin, current);
            if (invokeResult) {
                log.info("无属性变更：{}", current);
            } else {
                log.info("对象本身属性变更，进行持久化：{}", current);
                update(current);
            }
        }
    }

    protected abstract <D> void insert(D domain);

    protected abstract  <D> void update(D domain);

    private <D> void saveOrUpdate(CompareResult<D> compareResult) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Collection<D> elements = compareResult.getNewElements();
        if (CollectionUtils.isNotEmpty(elements)) {
            log.debug("新增对象，进行持久化：{}", elements);
            for (D domain :
                    elements) {
                persistSelf(domain);
            }
        }
        elements = compareResult.getDeletedElements();
        if (CollectionUtils.isNotEmpty(elements)) {
            log.debug("删除对象，进行持久化：{}", elements);
            // TODO 删除持久化流程
        }

        Collection<CollectionComparator.ModifiedElement<D>> modifiedElements = compareResult.getModifiedElements();
        if (CollectionUtils.isNotEmpty(modifiedElements)) {
            log.debug("修改对象，递归调用persistSaveOrUpdate：{}", modifiedElements);
            for (CollectionComparator.ModifiedElement<D> modifiedElement:
                    modifiedElements) {
                persistSaveOrUpdate(modifiedElement.getOrigin(), modifiedElement.getCurrent());
            }
        }
    }

    static <D> Field findIdField(Class<D> clazz) {
        List<Field> fieldList = findFieldByAnnotation(clazz, Id.class);
        if (CollectionUtils.isEmpty(fieldList)) {
            return null;
        }
        if (fieldList.size() > 1) {
            log.error("一个实体不能有多个Id属性：{}", fieldList);
            throw new PersistenceException("一个实体不能有多个Id属性" + clazz);
        }
        return fieldList.get(0);
    }

    static <D> List<Field> findFieldByAnnotation(Class<D> clazz, Class annotation) {
        List<Field> resultList = null;

        if (clazz.equals(Object.class)) {
            return resultList;
        }

        resultList = new ArrayList<>();

        // 查找对象自身属性
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field :
                declaredFields) {
            Annotation fieldAnnotation = field.getAnnotation(annotation);
            if (null != fieldAnnotation) {
                resultList.add(field);
            }
        }
        // 查找父类属性
        List<Field> superFields = findFieldByAnnotation(clazz.getSuperclass(), annotation);
        if (CollectionUtils.isNotEmpty(superFields)) {
            resultList.addAll(superFields);
        }

        return resultList;
    }

    public static void main(String[] args) {
        AbstractDirtyCheckRepository<User> repository = new AbstractDirtyCheckRepository<User>(){

            @Override
            protected <D> void insert(D origin) {

            }

            @Override
            protected <D> void update(D current) {

            }
        };
//        System.out.println(repository.findIdField(new User("", null, null, null).getClass()));
//        System.out.println(repository.findIdField(new Entity().getClass()));
        System.out.println(repository.findFieldByAnnotation(new User("", null, null, null).getClass(), Id.class));
        System.out.println(repository.findFieldByAnnotation(new User("", null, null, null).getClass(), OneToMany.class));
        System.out.println(repository.findFieldByAnnotation(new User("", null, null, null).getClass(), ManyToOne.class));
        System.out.println(repository.findFieldByAnnotation(new Entity().getClass(), Id.class));
    }
}
