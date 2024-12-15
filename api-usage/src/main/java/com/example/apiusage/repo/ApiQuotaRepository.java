package com.example.apiusage.repo;

import com.example.apiusage.entity.ApiQuota;
import com.example.apiusage.entity.Company;
import com.example.apiusage.entity.User;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiQuotaRepository extends JpaRepository<ApiQuota, Long> {
    Optional<ApiQuota> findByCompanyAndUser(Company company, User user);

    Optional<ApiQuota> findByCompanyAndApiNameAndCallDate(
            Company company, String apiName, LocalDate callDate);
}
