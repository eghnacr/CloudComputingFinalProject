package com.egehan.cloudcomputingfinalproject.controller;

import com.egehan.cloudcomputingfinalproject.service.FormRecognizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public final class RecognizerController {

    private final FormRecognizer formRecognizer;

    @PostMapping
    public ResponseEntity<String> recognize(@RequestParam("file") MultipartFile file) {
        String tckn = formRecognizer.recognize(file);
        return ResponseEntity.ok(tckn);
    }
}
