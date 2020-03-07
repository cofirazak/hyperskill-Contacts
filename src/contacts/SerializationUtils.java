package contacts;

import java.io.*;

/**
 * Util class for easy serialisation/deserialization of objects.
 */
class SerializationUtils {
    /**
     * Serialize the given object to the file.
     *
     * @param obj      object for serialisation.
     * @param fileName filename to save serialized object.
     * @throws IOException           if an I/O error occurs while writing stream header
     * @throws SecurityException     if untrusted subclass illegally overrides security-sensitive methods
     *                               or if a security manager exists and its checkWrite method denies write access to the file.
     * @throws FileNotFoundException if the file exists but is a directory rather than a regular file,
     *                               does not exist but cannot be created, or cannot be opened for any other reason.
     */
    static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * Deserialize to an object from the file.
     *
     * @param fileName name of file with serialised object.
     * @return Object
     * @throws IOException            if an I/O error occurs while reading stream header
     * @throws SecurityException      if a security manager exists and its checkRead method denies read access to the file.
     * @throws ClassNotFoundException if Class of a serialized object cannot be found.
     * @throws FileNotFoundException  if the file does not exist, is a directory rather than a regular file,
     *                                or for some other reason cannot be opened for reading.
     */
    static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}
