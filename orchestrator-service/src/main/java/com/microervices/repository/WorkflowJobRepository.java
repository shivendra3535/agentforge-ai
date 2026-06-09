package com.microervices.repository;

import com.microervices.models.WorkflowJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowJobRepository extends JpaRepository<WorkflowJob, Long> {
}
