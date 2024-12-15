package com.example.apiusage.rest;

import com.example.apiusage.entity.Company;
import com.example.apiusage.entity.User;
import com.example.apiusage.request.CompanyDTO;
import com.example.apiusage.request.UserDTO;
import com.example.apiusage.service.UserCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserCompanyController {

    @Autowired private UserCompanyService userCompanyService;

    @PostMapping("/company")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDTO companyDTO) {
        Company company = userCompanyService.createCompany(companyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = userCompanyService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
