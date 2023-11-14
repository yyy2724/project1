package com.api.shop_project.service.post;

import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.post.Post;
import com.api.shop_project.dto.response.post.PostSave;
import com.api.shop_project.exception.InvalidRequest;
import com.api.shop_project.exception.PostNotFound;
import com.api.shop_project.repository.member.MemberRepository;
import com.api.shop_project.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public List<PostSave> postListDo() {
        List<PostSave> postSaves = new ArrayList<>();
        List<Post> posts = postRepository.findAll();

        for (Post post : posts) {
            postSaves.add(PostSave.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .member(post.getMember())
                    .replies((post.getReplies()))
                    .createTime(post.getCreateTime())
                    .upDateTime(post.getUpdateTime())
                    .build());
        }

        return postSaves;


    }

    @Transactional
    public Post postInsertDo(PostSave postSave,
                             String email
                             ) {


        Member member =
                (Member) memberRepository.findByName(email).orElseThrow(IllegalArgumentException::new);

        Post post = postRepository.save(
                Post.builder()
                        .title(postSave.getTitle())
                        .content(postSave.getContent())
                        .writer(postSave.getWriter())
                        .member(member)
                        .build()
        );


        return post;
    }


    @Transactional
    public PostSave postDetail(Long id) {

        Post post =
                postRepository.findById(id).orElseThrow(PostNotFound::new);

        post.hit();

        return PostSave.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .member(post.getMember())
                .hit(post.getHit())
                .replies(post.getReplies())
                .createTime(post.getCreateTime())
                .upDateTime(post.getUpdateTime())
                .build();

    }

    @Transactional
    public int postUpdateOk(String email, PostSave postSave) {

        Member member = (Member) memberRepository.findByName(email).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));


        Post post = Post.builder()
                .id(postSave.getId())
                .writer(postSave.getWriter())
                .title(postSave.getTitle())
                .member(member)
                .content(postSave.getContent())
                .hit(postSave.getHit())
                .build();


        Long postId = postRepository.save(post).getId();


        postRepository.findById(postId).orElseThrow(PostNotFound::new);

        return 1;


    }

    public PostSave postUpdate(Long id) {

        Post post =
                postRepository.findById(id).orElseThrow(PostNotFound::new);


        return PostSave.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .hit(post.getHit())
                .createTime(post.getCreateTime())
                .build();


    }

    public void postDelete(Long id) {

        postRepository.findById(id).orElseThrow(PostNotFound::new);

        postRepository.deleteById(id);

    }

    public List<PostSave> searchPostList(String subject, String search) {
        List<PostSave> postSaves = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        if (subject.equals("content")) {
            posts = postRepository.findByContentContaining(search);
        } else if (subject.equals("title")) {
            posts = postRepository.findByTitleContaining(search);
        } else if (subject.equals("writer")) {
            posts = postRepository.findByWriterContaining(search);
        } else {
            posts = postRepository.findAll();
        }

        if (!posts.isEmpty()) {
            for (Post post : posts) {
                PostSave postSave = PostSave.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .writer(post.getWriter())
                        .createTime(post.getCreateTime())
                        .upDateTime(post.getUpdateTime())
                        .build();

                postSaves.add(postSave);
            }
        }
        return postSaves;


    }

    public Page<PostSave> postPagingList(Pageable pageable) {

        Page<Post> posts = postRepository.findAll(pageable);

        int nowPage = posts.getNumber(); // 현재 페이지 번호(요청 페이지 번호)
        Long totalCount = posts.getTotalElements(); // 전체 게시글 수
        int totalPage = posts.getTotalPages(); // 전체 페이지수
        int pageSize = posts.getSize(); // 한 페이지에 보이는 개수

        Page<PostSave> postVos = posts.map(PostSave::toBoardDto);

        return postVos;

    }

    private void updateHit1(PostSave postSave) {
        postRepository.updateHit1(postSave);
    }





    public Page<PostSave> postPagingList2(Pageable pageable, String subject, String search) {
        Page<Post> posts =null;

        if (subject == null) {
            posts = postRepository.findAll(pageable);
        } else if (subject.equals("title")) {
            posts = postRepository.findByTitleContains(pageable,search);
        } else if (subject.equals("writer")) {
            posts = postRepository.findByWriterContains(pageable,search);
        } else if (subject.equals("content")){
            posts = postRepository.findByContentContains(pageable,search);
        } else {
            posts = postRepository.findAll(pageable);
        }

        Page<PostSave> postVos = posts.map(PostSave::toBoardDto);

        return postVos;

    }
}






