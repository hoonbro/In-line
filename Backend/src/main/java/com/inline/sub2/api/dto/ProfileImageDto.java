package com.inline.sub2.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileImageDto {
    Long userId;
    MultipartFile file;
}
