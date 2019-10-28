package me.nathancole.api.file;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @RequestMapping(method = RequestMethod.POST, value = "api/files/upload")
    public String uploadFile(@RequestParam MultipartFile file) {
        return "https://i.hawkeyesocial.net/" + FileUtil.storeFile(file);
    }
}
