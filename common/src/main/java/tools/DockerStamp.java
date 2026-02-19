package tools;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
public class DockerStamp {
    public static void main(String[] args) throws IOException {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        int random = new Random().nextInt(100000);
        String stamp = "build-" + date + "-" + random;

        Path path = Paths.get("src/main/resources/static/stamp.txt");
        Files.createDirectories(path.getParent());
        Files.write(path, stamp.getBytes());
    }
}
