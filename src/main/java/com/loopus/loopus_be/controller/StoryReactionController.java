package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.AddReactionRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IStoryReactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@Validated
public class StoryReactionController {

    private final IStoryReactionService iStoryReactionService;

    @PostMapping("/reaction")
    @Operation(summary = "Thả cảm xúc (emoji)")
    public ResponseDto<Object> addReaction(@RequestBody @Valid AddReactionRequest addReactionRequest) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryReactionService.addReaction(addReactionRequest))
                .message("Thả cảm xúc thành công!")
                .build();
    }

    @DeleteMapping("/{storyId}/{reactionId}/reactions")
    @Operation(summary = "Gỡ reaction")
    public ResponseDto<Object> removeReaction(@PathVariable UUID reactionId) {
        iStoryReactionService.removeReaction(reactionId);
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Gỡ reaction thành công!")
                .build();
    }

    @GetMapping("/{storyId}/reactions")
    @Operation(summary = "Xem tất cả reaction theo story")
    public ResponseDto<Object> getReactions(@PathVariable UUID storyId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryReactionService.getReactions(storyId))
                .message("Xem tất cả reaction thành công!")
                .build();
    }

    @PutMapping("/reaction/{reactionId}")
    @Operation(summary = "Cập nhật reaction theo story")
    public ResponseDto<Object> updateReaction(
            @PathVariable UUID reactionId,
            @RequestBody @Valid @NotNull(message = "Vui lòng nhập content!") String newReaction
    ) {

        if (newReaction != null && newReaction.startsWith("\"") && newReaction.endsWith("\"")) {
            newReaction = newReaction.substring(1, newReaction.length() - 1);
        }

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iStoryReactionService.updateReaction(reactionId, newReaction))
                .message("Cập nhật reaction thành công!")
                .build();
    }
}
