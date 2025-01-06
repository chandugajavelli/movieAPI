package com.chandu.api_gateway.auth.utils;

import lombok.Data;

@Data
public class RefreshTokenRequest {

    private String refreshToken;
}