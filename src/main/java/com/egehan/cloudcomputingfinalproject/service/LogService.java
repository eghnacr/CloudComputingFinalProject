package com.egehan.cloudcomputingfinalproject.service;

import com.egehan.cloudcomputingfinalproject.entity.FormRecognizeRequestLog;
import com.egehan.cloudcomputingfinalproject.repository.FormRecognizeRequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogService {

    private final FormRecognizeRequestLogRepository formRecognizeRequestLogRepository;

    public void save(String operationLocation) {
        FormRecognizeRequestLog formRecognizeRequestLog = FormRecognizeRequestLog.builder()
                .operationLocation(operationLocation)
                .build();
        formRecognizeRequestLogRepository.save(formRecognizeRequestLog);
    }
}
