package com.ikolay.controller;

import com.ikolay.dto.requests.AddTransactionRequestDto;
import com.ikolay.dto.requests.AnnualProfitLossRequestDto;
import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.AllExpensesResponseDto;
import com.ikolay.dto.response.AnnualProfitLossResponseDto;
import com.ikolay.repository.entity.FinancialTransaction;
import com.ikolay.service.CompanyService;
import com.ikolay.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.ikolay.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRANSACTION)
public class TransactionController {
    private final TransactionService transactionService;

   @PostMapping(ADD) //Manager sayfasındaki işlem ekleme için hazırlandı.
    public ResponseEntity<FinancialTransaction> addTransaction(@RequestBody AddTransactionRequestDto dto){
       return ResponseEntity.ok(transactionService.add(dto));
   }

   @GetMapping(FINDALL) //Test için yazıldı kaldırılacak.
    public ResponseEntity<List<FinancialTransaction>> findAll(){
       return ResponseEntity.ok(transactionService.findAll());
   }

   @GetMapping(INCOMINGPAYMENTS) //Manager sayfasındaki "Yaklaşan harcamaları getir" componenti için hazırlandı.
    public ResponseEntity<List<FinancialTransaction>>  getIncomingPayments(Long companyId){
       return ResponseEntity.ok(transactionService.incomingPayments(companyId));
   }

   @PostMapping(PROFITLOSS) // Manager sayfasındaki "Yıllık kar zarar bilgisi"  componenti için hazırlandı.
    public  ResponseEntity<List<AnnualProfitLossResponseDto>> annualProfitLoss(@RequestBody AnnualProfitLossRequestDto dto){
       return ResponseEntity.ok(transactionService.annualProfitLoss(dto));
   }

   @GetMapping(ALLEXPENSES) //Manager sayfasındaki "Tüm harcamalar" componenti için hazırlandı.
    public ResponseEntity<List<AllExpensesResponseDto>> findAllExpenses(Long companyId){
       return ResponseEntity.ok(transactionService.findAllExpenses(companyId));
   }

}
