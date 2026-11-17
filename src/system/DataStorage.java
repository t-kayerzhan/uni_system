package system;

import java.io.*;

public class DataStorage {
    private static DataStorage instance;
    private String dataDirectory;

    private DataStorage() {
        this.dataDirectory = "data1/";
        new File(dataDirectory).mkdirs();
    }

    public static DataStorage getInstance() {
        if (instance == null) instance = new DataStorage();
        return instance;
    }

    public void saveAll(UniversitySystem system) {
        serialize(system, dataDirectory + "university.dat");
        System.out.println("System data saved successfully.");
    }



    public void serialize(Object obj, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
        }
    }

    public Object deserialize(String filename) {
        File file = new File(filename);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization error: " + e.getMessage());
            return null;
        }
    }
    
    public UniversitySystem loadAll() {
        Object obj = deserialize(dataDirectory + "university.dat");

        if (obj instanceof UniversitySystem) {
            System.out.println("√ Data restored.");
            return (UniversitySystem) obj;
        }

        return UniversitySystem.getInstance();
    }
}
