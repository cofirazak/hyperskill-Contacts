package contacts;

import java.io.*;

/**
 * Util class for easy serialisation/deserialization of objects.
 */
final class SerializationUtils {
    private SerializationUtils() {
    }

    static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try (ObjectOutput oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
        }
    }

    static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try (ObjectInput ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
    }
}
