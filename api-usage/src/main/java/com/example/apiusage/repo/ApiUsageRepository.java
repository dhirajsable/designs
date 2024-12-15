package com.example.apiusage.repo;

import com.example.apiusage.entity.ApiUsage;
import com.example.apiusage.entity.User;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiUsageRepository extends JpaRepository<ApiUsage, Long> {
    Optional<ApiUsage> findByUserAndApiNameAndCallDate(User user, String apiName, LocalDate date);
}
