package Pacman;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Record implements Serializable {
    private String name;
    private int score;
    private LocalDateTime time;

    public Record(String name, int score) {
        this.name = name;
        this.score = score;
        this.time = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }

//    public static void saveRecord(String filePath) {
//        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
//            outputStream.writeObject(GameMenu.records);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static List<Record> loadRecord(String filePath) {
//        List<Record> records = null;
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
//            records = (List<Record>) inputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return records;
//    }

}
