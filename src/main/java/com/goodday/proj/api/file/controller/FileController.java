package com.goodday.proj.api.file.controller;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileStore fileStore;
    private final FileRepository fileRepository;

    /**
     * 이미지 보기 /api/images/{storeFileName}
     *
     * @param filename
     * @return 이미지 경로
     * @throws MalformedURLException
     */
    @GetMapping("/image/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file : " + fileStore.getFullPath(filename));
    }

    /**
     * 파일 삭제
     * @param filename
     */
    @DeleteMapping("/file/{filename}")
    public void deleteFile(@PathVariable String filename) {
        fileStore.deleteFile(filename);
        int result = fileRepository.deleteFile(filename);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.deleteError);
        }
    }

    /**
     * 파일 다운로드
     * @param filename
     * @return
     * @throws MalformedURLException
     */
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable String filename) throws MalformedURLException {
        UploadFile uploadFile = fileRepository.findByFilename(filename);
        String storeFileName = uploadFile.getStoreFileName();
        String uploadFileName = uploadFile.getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
