package com.raed.baffounexpress.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.raed.baffounexpress.utils.B2Properties;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class B2Config {
    @Bean
    S3Client s3Client(B2Properties props) {
        AwsBasicCredentials creds = AwsBasicCredentials.create(props.getAccessKey(), props.getSecretKey());
        return S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(creds))
                .endpointOverride(URI.create(props.getEndpoint())).region(Region.of(props.getRegion()))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .httpClient(UrlConnectionHttpClient.builder().build()).build();

    }

}
