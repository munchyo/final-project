package com.goodday.proj.api.file.controller;

import com.goodday.proj.api.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    /**
     * 이미지 보기 /api/images/{storeFileName}
     * @param filename
     * @return 이미지 경로
     * @throws MalformedURLException
     */
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file : " + fileStore.getFullPath(filename));
    }

    // TODO filename을 받아와서 나중에 storeFileName = filename 으로 쿼리에서 찾아서 뿌리자
//    @GetMapping("/download/{filename}")
//    public ResponseEntity<Resource> downloadAttach(@PathVariable String filename) throws MalformedURLException {
//        Item item = itemRepository.findById(itemId);
//        String storeFileName = item.getAttachFile().getStoreFileName();
//        String uploadFileName = item.getAttachFile().getUploadFileName();
//        UrlResource resource = new UrlResource("file:" +
//                fileStore.getFullPath(filename));
//        log.info("uploadFileName={}", uploadFileName);
//        String encodedUploadFileName = UriUtils.encode(uploadFileName,
//                StandardCharsets.UTF_8);
//        String contentDisposition = "attachment; filename=\"" +
//                encodedUploadFileName + "\"";
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(resource);
//    }
}