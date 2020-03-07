package contacts;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.reflect.Modifier.isProtected;

/**
 * Abstraction for concrete realisation of contact types like Person and Organization.
 */
abstract class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String number;
    LocalDateTime creationDateTime;
    LocalDateTime lastEditDateTime;

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
            final Field[] declaredFields = Arrays.stream(currentClass.getDeclaredFields()).filter(filed -> isProtected(filed.getModifiers())).toArray(Field[]::new);
            Collections.addAll(allFields, declaredFields);
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

    abstract String[] getEditableFields();

    public void setNumber(String number) {
        this.number = number;
    }

    void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    void setLastEditDateTime(LocalDateTime lastEditDateTime) {
        this.lastEditDateTime = lastEditDateTime;
    }

    abstract void setFieldByName(String fieldName, String newValue);

    String filterPhoneNumber(String number) {
        if (RegexValidator.validatePhoneNumber(number)) {
            return number;
        } else {
            ContactBook.TERMINAL_COMMON.showWrongNumberFormat();
            return "";
        }
    }

    abstract void showContactsListItem(int index);

    abstract void showEditableFields();
}
