package com.ikolay.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.ikolay.constant.EndPoints.FINDIDBYEMAIL;


@FeignClient(url = "http://localhost:7072/api/v1/user",decode404 = true,name = "company-user")
public interface IUserManager {

    @GetMapping(FINDIDBYEMAIL)
    ResponseEntity<Long> findIdByEmail(@PathVariable String email);


    @GetMapping("/monthlyemployeesalary/{companyId}")
    ResponseEntity<Double> findTotalEmployeeSalary(@PathVariable Long companyId);
}
