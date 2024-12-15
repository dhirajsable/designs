package com.example.apiusage.service;

import com.example.apiusage.entity.Company;
import com.example.apiusage.entity.User;
import com.example.apiusage.repo.CompanyRepository;
import com.example.apiusage.repo.UserRepository;
import com.example.apiusage.request.CompanyDTO;
import com.example.apiusage.request.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCompanyService {

    @Autowired private UserRepository userRepository;

    @Autowired private CompanyRepository companyRepository;

    public Company createCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        return companyRepository.save(company);
    }

    public User createUser(UserDTO userDTO) {
        Company company =
                companyRepository
                        .findById(userDTO.getCompanyId())
                        .orElseThrow(() -> new RuntimeException("Company not found"));

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setCompany(company);
        return userRepository.save(user);
    }
}
