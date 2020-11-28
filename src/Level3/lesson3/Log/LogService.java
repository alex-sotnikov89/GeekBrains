package Level3.lesson3.Log;


import java.io.*;
import java.util.Date;


public class LogService {
    File file;

    public LogService(File file) {
        this.file = file;
    }

    public void saveHistoryMassage(String message) {
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(file,
                            true
                    )
            );

            bw.newLine();
            bw.write(String.format(
                    "%s ",
                    new Date()
            ) + " : " + message);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String showHistory(int lines) {
        StringBuilder sb = new StringBuilder();
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            long fileLength = file.length() - 1;
            int reachedLines = 0;

            raf.seek(fileLength);

            for (long ptr = fileLength; ptr >= 0 ; ptr--) {
                raf.seek(ptr);
                char c = (char) raf.read();

                if (c == '\n') {
                    reachedLines++;

                    if (reachedLines == lines) {
                        break;
                    }
                }

                sb.append(c);
            }

            sb.reverse();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
