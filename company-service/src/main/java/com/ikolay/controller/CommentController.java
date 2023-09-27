package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;

import com.ikolay.constant.EndPoints;
import com.ikolay.dto.requests.CommentAddDto;
import com.ikolay.dto.response.CommentAddResponse;
import com.ikolay.dto.response.GetAllCommentsResponseDto;
import com.ikolay.repository.entity.Comment;
import com.ikolay.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMMENT)
public class CommentController {

    private final CommentService commentService;
    @PostMapping(ADDCOMMENT) //Comment eklemek için hazırlandı. Eklenen commentler pending olarak eklenip admin sayfasında kontrol ediliyor.
    public ResponseEntity<CommentAddResponse> addCommment(CommentAddDto dto){
        commentService.commentAddForAdmin(dto);
        return ResponseEntity.ok(CommentAddResponse.builder()
                        .message("Yorumunuz IKOLAY admin kontrolüne gönderilmiştir!!")
                .build());
    }
    @GetMapping(FINDALLCOMMENTFORADMIN) //Admin sayfasındaki commentlerin görüntülenmesinde kullanılan metod.
    public ResponseEntity<List<Comment>> findAllPendingComments(){
        return ResponseEntity.ok(commentService.findAllPendingComments());
    }

    @GetMapping(FINDALLCOMMENTFORGUEST) //Guest sayfasındaki commentlerin görüntülenmesinde kullanılan metod.
    public ResponseEntity<List<GetAllCommentsResponseDto>> findAllForGuest(Long companyId){
        return ResponseEntity.ok(commentService.getAllCommentsForGuest(companyId));
    }

    @GetMapping("/acceptcomment/{id}") //Admin sayfasındaki yorum onaylama işlemi için yapıldı.
    ResponseEntity<Boolean> acceptComment(@PathVariable Long id){
        return ResponseEntity.ok(commentService.acceptComment(id));
    }

    @GetMapping("/rejectcomment/{id}") //Admin sayfasındaki yorum reddetme işlemi için yapıldı.
    ResponseEntity<Boolean> rejectComment(@PathVariable Long id){
        return ResponseEntity.ok(commentService.rejectComment(id));
    }

    @GetMapping("/finduserscomment/{userId}") //Personel sayfasındaki yorum yazma özelliği için hazırlandı.
    ResponseEntity<Comment> getUsersComment(@PathVariable Long userId){
        return ResponseEntity.ok(commentService.findByUserId(userId));
    }
}
