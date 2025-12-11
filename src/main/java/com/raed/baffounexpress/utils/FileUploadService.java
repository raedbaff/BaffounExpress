package com.raed.baffounexpress.utils;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class FileUploadService {
    private final S3Client s3Client;
    private final B2Properties props;

    public String upload(MultipartFile file) throws Exception {
        String key = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        PutObjectRequest request = PutObjectRequest.builder().bucket(props.getBucket()).key(key)
                .contentType(file.getContentType()).build();
        s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return key;

    }
    public GetObjectRequest getFileRequest(String key) {
        return GetObjectRequest.builder()
                .bucket(props.getBucket())
                .key(key)
                .build();
    }

    public void streamFile(String key, OutputStream out) throws IOException {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(props.getBucket())
                .key(key)
                .build();

        try (ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(request)) {
            s3Object.transferTo(out);
        }
    }

}
