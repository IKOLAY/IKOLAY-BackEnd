package com.ikolay.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100, "Parametre hatası", HttpStatus.BAD_REQUEST),
    COMPANY_NOT_CREATED(4121, "Şirket İsmi Ve Vergi Numarası Boş Bırakılamaz", HttpStatus.BAD_REQUEST),
    EMAIL_EXIST(4110, "Email Adresi Zaten Mevcut.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4114, "Böyle bir kullanıcı bulunamadı!", HttpStatus.NOT_FOUND),
    USER_NOT_CREATED(4111, "Kullanıcı oluşturulamadı!", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE(4113, "Aktive edilmemiş hesap. Lütfen hesabınızı aktive ettikten sonra tekrar deneyin.", HttpStatus.FORBIDDEN),
    DOLOGIN_EMAILORPASSWORD_NOTEXISTS(1006,"Email adı veya şifre hatalı.",HttpStatus.BAD_REQUEST),
    MANAGER_ALREADY_CONFIRMED(1007,"Firma yetkilisinin hesabı zaten onaylanmış.",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4116, "Geçersiz token!", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(4117, "Token oluşturulamadı", HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_MISMATCH(4115, "Aktivasyon kodu hatalı!", HttpStatus.BAD_REQUEST),
    STATUS_NOT_FOUND(4260,"Boyle bir kullanıcı durumu bulunamadı" ,HttpStatus.BAD_REQUEST );

    private int code;
    private String message;
    private HttpStatus status;
}
