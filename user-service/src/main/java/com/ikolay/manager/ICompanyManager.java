package com.ikolay.manager;

import com.ikolay.dto.response.AllConfirmationInfoResponseDto;
import com.ikolay.dto.response.ConfirmationInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ikolay.constant.EndPoints.COMPANYINFOFORCONFIRMATION;

@FeignClient(url = "http://localhost:7073/api/v1/company",decode404 = true,name = "user-company")
public interface ICompanyManager {

    @PostMapping(COMPANYINFOFORCONFIRMATION)
    public ResponseEntity<List<ConfirmationInfoResponseDto>> companyInfoForConfirmation(@RequestBody List<Long> companyIds);

}
