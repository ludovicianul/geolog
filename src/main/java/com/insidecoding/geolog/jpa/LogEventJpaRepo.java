package com.insidecoding.geolog.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insidecoding.geolog.jpa.entity.LogFailedEntity;

public interface LogEventJpaRepo extends JpaRepository<LogFailedEntity, Long> {

	LogFailedEntity findByIp(String ip);
}
