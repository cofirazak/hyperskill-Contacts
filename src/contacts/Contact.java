package contacts;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.reflect.Modifier.isPublic;

/**
 * Abstraction for concrete realisation of contact types like Person and Organization.
 */
abstract class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    public String number;
    LocalDateTime creationDateTime;
    LocalDateTime lastEditDateTime;

    void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    void setLastEditDateTime(LocalDateTime lastEditDateTime) {
        this.lastEditDateTime = lastEditDateTime;
    }

    /**
     * Get all the fields from instance called this method and all public fields from its superclasses.
     *
     * @return List<Field>
     */
    List<Field> getAllFieldNames() {
        final List<Field> allFields = new ArrayList<>();
        Collections.addAll(allFields, this.getClass().getDeclaredFields());
        Class<?> currentClass = this.getClass().getSuperclass();
        while (currentClass != null) {
            final Field[] declaredFields = Arrays.stream(currentClass.getDeclaredFields()).filter(filed -> isPublic(filed.getModifiers())).toArray(Field[]::new);
            Collections.addAll(allFields, declaredFields);
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

    /**
     * Recursively get a field by name from given Class and its superclasses.
     *
     * @param cls Class in which you want find the field.
     * @param fieldName name of field you want to find.
     * @return Field
     * @throws NoSuchFieldException if given field name is not found.
     */
    Field getFieldByName(Class<?> cls, String fieldName) throws NoSuchFieldException {
        Class<?> currentClass = cls;
        try {
            return currentClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            currentClass = cls.getSuperclass();
            if (currentClass != null) {
                return getFieldByName(currentClass, fieldName);
            } else {
                throw e;
            }
        }
    }

    /**
     * Takes a string that represents a field that the class is able to change and its new value.
     *
     * @param fieldName name of field to which set the new value
     * @param newValue value for setting to field
     */
    abstract void setValueToField(String fieldName, String newValue);

    /**
     * Prints message to stout that lists all fields the user can change for concrete realisation.
     */
    abstract void showEditMenuFields();

    /**
     * @param index number prepending each list item printed to user.
     */
    abstract void showListItems(int index);
}
