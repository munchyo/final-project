package com.goodday.proj.api.file.repository;

import com.goodday.proj.api.file.model.UploadFile;

public interface FileRepository {
    int deleteFile(String filename);

    UploadFile findByFilename(String filename);
}
