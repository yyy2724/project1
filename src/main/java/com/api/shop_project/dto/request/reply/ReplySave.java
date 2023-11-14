package com.api.shop_project.dto.request.reply;

import com.api.shop_project.domain.BaseTime;
import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ReplySave {

    private Long id;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private String writer;

    private Post post;

    private Member member;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Builder
    public ReplySave(Long id, String content, String writer, Post post, Member member, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.post = post;
        this.member = member;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
