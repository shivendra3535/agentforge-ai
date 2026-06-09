package com.microervices.repository;


import com.microervices.models.AgentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentResultRepository  extends JpaRepository<AgentResult, Long> {
    List<AgentResult> findByJobId(Long jobId);
}
