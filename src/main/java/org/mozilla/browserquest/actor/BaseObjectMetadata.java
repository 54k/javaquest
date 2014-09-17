package org.mozilla.browserquest.actor;

import io.gwynt.util.AbstractConstant;
import io.gwynt.util.ConstantPool;

import java.util.UUID;

public final class BaseObjectMetadata<T> extends AbstractConstant<BaseObjectMetadata<T>> {

    private static ConstantPool<BaseObjectMetadata<Object>> pool = new ConstantPool<BaseObjectMetadata<Object>>() {
        @Override
        protected BaseObjectMetadata<Object> newConstant(int id, String name) {
            return new BaseObjectMetadata<>(id, name);
        }
    };

    public static final BaseObjectMetadata<UUID> ID = valueOf("ID");
    public static final BaseObjectMetadata<BaseObjectDefinition> DEFINITION = valueOf("DEFINITION");

    private BaseObjectMetadata(int id, String name) {
        super(id, name);
    }

    @SuppressWarnings("unchecked")
    public static <T> BaseObjectMetadata<T> valueOf(Class<?> firstNameComponent, String secondNameComponent) {
        return (BaseObjectMetadata<T>) pool.valueOf(firstNameComponent, secondNameComponent);
    }

    @SuppressWarnings("unchecked")
    public static <T> BaseObjectMetadata<T> valueOf(String name) {
        return (BaseObjectMetadata<T>) pool.valueOf(name);
    }
}
