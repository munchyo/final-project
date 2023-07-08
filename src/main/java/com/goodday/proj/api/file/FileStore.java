package com.goodday.proj.api.file;

import com.goodday.proj.api.file.model.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    private final String wallpaperDir = System.getProperty("user.home");

    private final String getDirPath = wallpaperDir + fileDir;

    public String getFullPath(String filename) {
        return getDirPath + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        File folder = new File(getDirPath);
        if (!folder.exists()) folder.mkdirs();
        multipartFile.transferTo(new File(getFullPath(storeFilename)));
        return new UploadFile(originalFilename, storeFilename);
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public void deleteFile(String filename) {
        File file = new File(getFullPath(filename));
        if(file.exists()) {
            file.delete();
        }
    }

}
