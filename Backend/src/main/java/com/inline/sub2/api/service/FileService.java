package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.ProfileImageDto;

import java.io.IOException;

public interface FileService {
    void fileUpload(ProfileImageDto profileImageDto) throws IOException;
}
