package com.ikolay.manager;


import com.ikolay.dto.requests.AdvancePaymentRequestDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "${feign.transaction}",decode404 = true,name = "user-transaction")
public interface ITransactionManager {

    @PostMapping("/addadvancepayment") //Onaylanan ya da reddedilen avansların harcama olarak gösterilmesini sağlayan metod (FEIGN)
    public ResponseEntity<Boolean> addAdvancePayment(@RequestBody AdvancePaymentRequestDto dto);

}
