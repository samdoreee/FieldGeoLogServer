package com.samdoree.fieldgeolog.Comment.Controller;

import com.samdoree.fieldgeolog.Comment.DTO.CommentRequestDTO;
import com.samdoree.fieldgeolog.Comment.DTO.CommentResponseDTO;
import com.samdoree.fieldgeolog.Comment.Service.CommentModifyService;
import com.samdoree.fieldgeolog.Comment.Service.CommentRegisterService;
import com.samdoree.fieldgeolog.Comment.Service.CommentRemoveService;
import com.samdoree.fieldgeolog.Comment.Service.CommentSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRegisterService commentRegisterService;
    private final CommentSearchService commentSearchService;
    private final CommentModifyService commentModifyService;
    private final CommentRemoveService commentRemoveService;


    @PostMapping()
    public CommentResponseDTO addComment(@PathVariable Long articleId, @Valid @RequestBody CommentRequestDTO commentRequestDTO) throws Exception {
        return commentRegisterService.addComment(articleId, commentRequestDTO);
    }

    @GetMapping()
    public List<CommentResponseDTO> getAllCommentList(@PathVariable Long articleId) {
        return commentSearchService.getAllCommentList(articleId);
    }

    @GetMapping("/{commentId}")
    public CommentResponseDTO getOneComment(@PathVariable Long articleId, @PathVariable Long commentId) {
        return commentSearchService.getOneComment(articleId, commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDTO modifyComment(@PathVariable Long articleId, @PathVariable Long commentId, @Valid @RequestBody CommentRequestDTO commentRequestDTO) throws Exception {
        return commentModifyService.modifyComment(articleId, commentId, commentRequestDTO);
    }

    @DeleteMapping("/{commentId}")
    public Boolean removeComment(@PathVariable Long articleId, @PathVariable Long commentId) {
        return commentRemoveService.removeComment(articleId, commentId);
    }
}
