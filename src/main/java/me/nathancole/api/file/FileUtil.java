package me.nathancole.api.file;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUtil {

    public static String storeFile(MultipartFile multipartFile) {
        String filename = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        while (new File("images/" + filename).exists())
            filename = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        try {
            if (multipartFile.isEmpty()) {
                throw new IOException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                throw new IOException("Cannot store file with relative path outside current directory " + filename);
            }

            Path path = Paths.get("images/").resolve(filename);

            File file = new File(path.toString());
            if (!Files.exists(Paths.get("images/")))
                if (file.mkdirs())
            if (!file.exists())
                file.createNewFile();


            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, Paths.get("images/").resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
                return filename;
            }
        } catch (IOException e) {
            try {
                throw new IOException("Failed to store file " + filename, e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
