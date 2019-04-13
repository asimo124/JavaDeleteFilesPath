package info.alexhawley;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String mainFolderPath = "";

        String file = "C:\\Temp\\delete_files_path.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                mainFolderPath = line;
                break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try (Stream<Path> walk = Files.walk(Paths.get(mainFolderPath))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

            for (String filePath: result) {
                System.out.println("filePath: " + filePath);

                //*/
                if (filePath.contains("_remote.repositories")) {
                    Path path = Paths.get(filePath);
                    try {
                        Files.delete(path);
                    } catch (NoSuchFileException x) {
                        System.err.format("%s: no such" + " file or directory%n", path);
                    } catch (DirectoryNotEmptyException x) {
                        System.err.format("%s not empty%n", path);
                    } catch (IOException x) {
                        // File permission problems are caught here.
                        System.err.println(x);
                    }
                }
                //*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
