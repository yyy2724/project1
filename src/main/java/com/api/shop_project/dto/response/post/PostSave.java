package com.api.shop_project.dto.response.post;

import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.post.Post;
import com.api.shop_project.domain.post.Reply;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostSave {

    private Long id;

    private String title;

    private String content;

    private String writer;

    private Member member;

    private int hit;

    private LocalDateTime createTime;

    private LocalDateTime upDateTime;

    private List<Reply> replies;

    public void postsave(Long id, String title, String content, String writer,
                         Member member, LocalDateTime createTime, LocalDateTime upDateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.member = member;
        this.createTime = createTime;
        this.upDateTime = upDateTime;
    }

    public static PostSave toBoardDto(Post post) {

        PostSave postSave = new PostSave();
        postSave.setId(post.getId());
        postSave.setTitle(post.getTitle());
        postSave.setContent(post.getContent());
        postSave.setWriter(post.getWriter());
        postSave.setHit(post.getHit());
        postSave.setMember(post.getMember());
        postSave.setCreateTime(post.getCreateTime());
        postSave.setUpDateTime(post.getUpdateTime());

        return postSave;
    }




}
