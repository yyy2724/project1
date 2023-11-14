package com.api.shop_project.repository.post;

import com.api.shop_project.domain.post.Post;
import com.api.shop_project.domain.post.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {


    List<Reply> findAllByPost(Post post);

    Reply findByMemberIdAndPostId(Long memberId, Long postId);

}
