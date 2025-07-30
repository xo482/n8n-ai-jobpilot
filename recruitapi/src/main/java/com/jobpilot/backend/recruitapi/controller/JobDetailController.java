package com.jobpilot.backend.recruitapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs/relay/view-detail")
public class JobDetailController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, String>>> getJobDetailJson(@RequestParam("rec_idx") String recIdx) throws IOException {
        // HTML 파일 이름: rec_51303460.html
        String fileName = "rec_" + recIdx + ".html";
        ClassPathResource htmlFile = new ClassPathResource("/static/" + fileName);

        if (!htmlFile.exists()) {
            return ResponseEntity.status(404).body(List.of(
                    Map.of("data", "<!-- 해당 rec_idx에 대한 HTML 파일이 존재하지 않습니다. -->")
            ));
        }

        String htmlContent = StreamUtils.copyToString(htmlFile.getInputStream(), StandardCharsets.UTF_8);

        Map<String, String> responseItem = new HashMap<>();
        responseItem.put("data", htmlContent);

        return ResponseEntity.ok(List.of(responseItem));
    }
}
