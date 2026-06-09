package com.microervices.models;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_jobs",
        indexes = {
                @Index(
                        name = "idx_job_status",
                        columnList = "status"
                )
        }
)
@EntityListeners(
        AuditingEntityListener.class
)
public class WorkflowJob {

    public enum JobStatus {
        PENDING,
        RUNNING,
        DONE,
        FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_prompt"
    , columnDefinition = "TEXT")
    private String userPrompt;


    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @CreatedDate
    @Column(name = "created_at",    nullable = false,
            updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public WorkflowJob(String userPrompt, JobStatus status, LocalDateTime createdAt, LocalDateTime completedAt) {
        this.userPrompt = userPrompt;
        this.status = status;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }
    public WorkflowJob(){

    }

    public Long getId() {
        return id;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
