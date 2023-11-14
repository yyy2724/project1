package com.api.shop_project.service.post;

import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.post.Post;
import com.api.shop_project.domain.post.Reply;
import com.api.shop_project.dto.request.reply.ReplySave;
import com.api.shop_project.exception.PostNotFound;
import com.api.shop_project.exception.ReplyNotFound;
import com.api.shop_project.repository.member.MemberRepository;
import com.api.shop_project.repository.post.PostRepository;
import com.api.shop_project.repository.post.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;




    @Transactional
    public Reply replyInsert(Long postId, String content, String email) {



        ReplySave replySave = new ReplySave();

        Member member = (Member) memberRepository.findByName(email).orElseThrow(()-> new IllegalArgumentException("이메일 찾을 수 없습니다."));

        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        Reply reply = replyRepository.save(Reply.builder()
                .content(content)
                .writer(member.getName())
                .member(member)
                .post(post)
                .build());

        return reply;
    }

    public List<ReplySave> replyList(Long id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.get();
        List<ReplySave> replyVos = new ArrayList<>();
        List<Reply> replyList = replyRepository.findAllByPost(post);
        for (Reply reply : replyList) {
            ReplySave replyVo = ReplySave.builder()
                    .id(reply.getId())
                    .content(reply.getContent())
                    .writer(reply.getWriter())
                    .member(reply.getMember())
                    .post(reply.getPost())
                    .createTime(reply.getCreateTime())
                    .updateTime(reply.getUpdateTime())
                    .build();

            replyVos.add(replyVo);
        }

        return replyVos;

    }


    @Transactional
    public Reply replyUpdateOk(String email, Long replyId, Long memberId, Long postId, String content) {

        Member member = (Member) memberRepository.findByName(email).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));


        Reply reply = Reply.builder()
                .id(replyId)
                .content(content)
                .member(member)
                .writer(member.getName())
                .post(post)
                .build();


        return replyRepository.save(reply);

    }


    public Reply replyDelete(Long id) {

        Reply reply = replyRepository.findById(id).orElseThrow(ReplyNotFound::new);

        replyRepository.delete(reply);

        return reply;
    }


    public ReplySave replyUpdate(Long id) {

        Optional<Reply> optionalReplySave =
                Optional.ofNullable(replyRepository.findById(id).orElseThrow(()->{
                    return new IllegalArgumentException("업데이트할 아이디 없음");
                }));

        if (optionalReplySave.isPresent()){

            ReplySave replySave =
                    ReplySave.builder()
                            .id(optionalReplySave.get().getId())
                            .content(optionalReplySave.get().getContent())
                            .writer(optionalReplySave.get().getWriter())
                            .createTime(optionalReplySave.get().getCreateTime())
                            .updateTime(optionalReplySave.get().getUpdateTime())
                            .post(optionalReplySave.get().getPost())
                            .build();

            return replySave;
        }
        return null;

    }

}






