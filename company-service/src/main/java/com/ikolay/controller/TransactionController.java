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

   @GetMapping(ADD)
    public ResponseEntity<FinancialTransaction> addTransaction(AddTransactionRequestDto dto){
       return ResponseEntity.ok(transactionService.add(dto));
   }

   @GetMapping(FINDALL)
    public ResponseEntity<List<FinancialTransaction>> findAll(){
       return ResponseEntity.ok(transactionService.findAll());
   }

   @GetMapping(INCOMINGPAYMENTS)
    public ResponseEntity<List<FinancialTransaction>>  getIncomingPayments(Long companyId){
       return ResponseEntity.ok(transactionService.incomingPayments(companyId));
   }

   @PostMapping(PROFITLOSS)
    public  ResponseEntity<List<AnnualProfitLossResponseDto>> annualProfitLoss(@RequestBody AnnualProfitLossRequestDto dto){
       return ResponseEntity.ok(transactionService.annualProfitLoss(dto));
   }

   @GetMapping("/test")
    public ResponseEntity<List<AllExpensesResponseDto>> test(Long companyId){
       return ResponseEntity.ok(transactionService.findAllExpenses(companyId));
   }


}
