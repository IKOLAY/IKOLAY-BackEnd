package com.ikolay.controller;

import com.ikolay.dto.requests.AddTransactionRequestDto;
import com.ikolay.dto.requests.AdvancePaymentRequestDto;
import com.ikolay.dto.requests.AnnualProfitLossRequestDto;
import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.AllExpensesResponseDto;
import com.ikolay.dto.response.AnnualProfitLossResponseDto;
import com.ikolay.dto.response.FindMyExpensesResponseDto;
import com.ikolay.dto.response.GetCompanysPendingPaymentsResponseDto;
import com.ikolay.repository.entity.FinancialTransaction;
import com.ikolay.repository.enums.ETransactionStatus;
import com.ikolay.service.CompanyService;
import com.ikolay.service.FileService;
import com.ikolay.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.ikolay.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRANSACTION)
public class TransactionController {
    private final TransactionService transactionService;
   @PostMapping(ADD) //Manager sayfasındaki işlem ekleme için hazırlandı.
    public ResponseEntity<FinancialTransaction> addTransaction(@RequestBody @Valid AddTransactionRequestDto dto){
       return ResponseEntity.ok(transactionService.add(dto));
   }

   @PostMapping("/addadvancepayment") //Onaylanan ya da reddedilen avansların harcama olarak gösterilmesini sağlayan metod (FEIGN)
   public ResponseEntity<Boolean> addAdvancePayment(@RequestBody AdvancePaymentRequestDto dto){
    return ResponseEntity.ok(transactionService.addAdvancePayment(dto));
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

   @GetMapping("/getcurrencies") //Enum listesi döndürüyor ve Önyüz dropdown için hazırlandı.
    public ResponseEntity<List<String>> getAllCurrencyList(){
       return ResponseEntity.ok(transactionService.getAllCurrencyList());
   }

   @GetMapping("/getexpensetypes") //Enum listesi döndürüyor ve Önyüz dropdown için hazırlandı.
   public ResponseEntity<List<String>> getExpenseTypesForEmployee(){
       return ResponseEntity.ok(transactionService.getExpenseTypesForEmployee());
   }

    @GetMapping("/findmyexpenserequests/{id}") //Personel sayfasındaki personel tarafından oluşturulan harcamaları getirmek için hazırlandı.
    public ResponseEntity<List<FindMyExpensesResponseDto>> findEmployeesExpenses(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.findEmployeesExpenses(id));
    }

    @GetMapping("/getcompanyspendingpayments/{companyId}") //Manager sayfasındaki personelin oluşturduğu ödemeleri görebilmek için hazırlandı.
    public ResponseEntity<List<GetCompanysPendingPaymentsResponseDto>> findCompanysPendingPayments(@PathVariable Long companyId){
       return ResponseEntity.ok(transactionService.findByCompanyIdAndStatus(companyId, ETransactionStatus.PENDING));
    }

    @GetMapping("/confirmpayment/{id}") //Personel harcama isteğini onaylamak için hazırlandı.
    public ResponseEntity<Boolean> confirmPayment(@PathVariable Long id){
       return ResponseEntity.ok(transactionService.confirmPayment(id));
    }

    @GetMapping("/rejectpayment/{id}") //Personel harcama isteğini reddetmek için hazırlandı.
    public ResponseEntity<Boolean> rejectPayment(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.rejectPayment(id));
    }
}
