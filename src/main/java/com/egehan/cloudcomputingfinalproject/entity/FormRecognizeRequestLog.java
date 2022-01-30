package com.egehan.cloudcomputingfinalproject.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "form_recognize_request_log")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormRecognizeRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "operation_location", nullable = false, length = 500)
    private String operationLocation;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

}
