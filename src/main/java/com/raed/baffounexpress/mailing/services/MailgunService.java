package com.raed.baffounexpress.mailing.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MailgunService {
    @Value("${mailgun.from}")
    private String from;
    private final WebClient webClient;
    public Mono<String> sendMail(String to, String subject, String text) {
        return this.webClient.post().uri("/messages").contentType(MediaType.APPLICATION_FORM_URLENCODED).body(
            BodyInserters.fromFormData("from",from).with("to", to).with("subject", subject).with("text",text)
        ).retrieve().onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class).flatMap(err -> {
            System.out.println("Error");
            System.out.println(err);
            return Mono.error(new RuntimeException("Mailgun error:" + response.statusCode()));
        })).bodyToMono(String.class);
        
    }

}
