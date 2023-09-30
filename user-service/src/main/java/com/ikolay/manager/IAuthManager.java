package com.ikolay.manager;

import com.ikolay.dto.requests.UpdateUserRequestDto;
import com.ikolay.dto.response.ConfirmationInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.ikolay.constant.EndPoints.DELETE;
import static com.ikolay.constant.EndPoints.UPDATE;

@FeignClient(url = "http://localhost:7071/api/v1/auth",decode404 = true,name = "user-auth")
public interface IAuthManager {

    @PostMapping(UPDATE) //user-service içindeki güncellemelerin buraya da taşınması için kullanıldı.
    ResponseEntity<Boolean> updateAuthInfo(@RequestBody UpdateUserRequestDto dto);

    @DeleteMapping(DELETE+"/{id}")
    ResponseEntity<Boolean> deleteEmployee(@PathVariable Long id);
}
