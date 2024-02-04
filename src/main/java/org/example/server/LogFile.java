package org.example.server;

import java.io.*;
import java.util.List;

class LogFile {
    private File logFile;
    private final String FILE_NAME = "log_file.txt";

    LogFile() {
        logFile = new File(FILE_NAME);
        try {
            if (!logFile.exists()) {
                this.logFile.createNewFile();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<String> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.logFile))) {
            List<String> stringList = reader.lines().toList();
            int size = stringList.size();
            if (size > 15) {
                stringList = stringList.subList(size - 15, size);
            }
            return stringList;
        } catch (RuntimeException | IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void write(String addMessage) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.logFile));
        List<String> stringList = reader.lines().toList();
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.logFile));
        for (String s : stringList) {
            writer.append(s);
            writer.newLine();
        }
        writer.write(addMessage);
        writer.flush();
        writer.close();
        reader.close();
    }
}
