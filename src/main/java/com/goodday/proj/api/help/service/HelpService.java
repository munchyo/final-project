package com.goodday.proj.api.help.service;

import com.goodday.proj.api.help.dto.HelpForm;

import java.io.IOException;

public interface HelpService {
    int writeHelp(HelpForm form) throws IOException;
}
