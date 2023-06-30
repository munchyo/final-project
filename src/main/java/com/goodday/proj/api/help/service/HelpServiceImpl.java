package com.goodday.proj.api.help.service;

import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.help.dto.HelpForm;
import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.repository.HelpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HelpServiceImpl implements HelpService {

    private final HelpRepository helpRepository;
    private final FileStore fileStore;

    @Override
    public int writeHelp(HelpForm form) throws IOException {
        if (form.getImages().stream().filter(file -> !file.getOriginalFilename().equals("")).findFirst().isPresent()) {
            List<UploadFile> images = fileStore.storeFiles(form.getImages());
            return helpRepository.saveHelpAndImages
                    (new Help(form.getHelpType(), form.getHelpTitle(), form.getHelpContent(), form.getMemberNo(), images));
        }
        return helpRepository.saveHelp
                (new Help(form.getHelpType(), form.getHelpTitle(), form.getHelpContent(), form.getMemberNo()));
    }
}
