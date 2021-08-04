package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.UserRegistDto;

public interface EmailService {
    public void sendEmail(UserRegistDto user);

    public String sendPassword(String email);
}
