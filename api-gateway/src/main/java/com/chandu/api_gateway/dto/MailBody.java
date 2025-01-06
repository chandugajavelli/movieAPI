package com.chandu.api_gateway.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}
