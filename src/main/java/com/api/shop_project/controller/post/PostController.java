package com.api.shop_project.controller.post;


import com.api.shop_project.config.MyUserDetails;
import com.api.shop_project.domain.post.Post;
import com.api.shop_project.dto.request.reply.ReplySave;
import com.api.shop_project.dto.response.post.PostSave;
import com.api.shop_project.service.post.PostService;
import com.api.shop_project.service.post.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final ReplyService replyService;

    // 공지사항 목록
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_SELLER')")
    @GetMapping({"", "/postList"})
    public String post(@PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(value = "subject", required = false) String subject,
                       @RequestParam(value = "search", required = false) String search,
                       Model model) {


        Page<PostSave> postList = postService.postPagingList2(pageable, subject, search);

        Long totalCount = postList.getTotalElements();
        int pagesize = postList.getSize();
        int nowPage = postList.getNumber();
        int totalPage = postList.getTotalPages();
        int blockNum = 3;

        int startPage =
                (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);

        int endPage =
                (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);


        for (int i = startPage; i <= endPage; i++) {
            System.out.println(i + " , ");
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("postVo", postList);


        model.addAttribute("postList", postList);

        return "post/postList";


    }


    // 글작성 페이지
    @GetMapping("/postInsert")
    public String postInsert() {

        return "post/postInsert";
    }

    // 글작성
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/postInsert")
    public String postInsertPost(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                 PostSave postSave,
                                 Model model) {

        System.out.println("넘어감?");
        String email = myUserDetails.getUsername();
//        System.out.println(email);

        Post post = postService.postInsertDo(postSave, email);

//        model.addAttribute("postVo", post);

        return "redirect:/post/postList?page=0&search=&subject=";
//        return "index";
    }

    // 게시글 자세히 보기
    @GetMapping("/postDetail/{id}")
    public String postDetail(@PathVariable("id") Long id, Model model) {



        PostSave postSave = postService.postDetail(id);
        model.addAttribute("post", postSave);
        List<ReplySave> replyList = replyService.replyList(postSave.getId());

        if (!replyList.isEmpty()) {
            model.addAttribute("replyList", replyList);
        }


        return "post/postDetail";

    }

    // update 버튼 클릭시
    @PostMapping("/postUpdateOk")
    public String postUpdateOk(@AuthenticationPrincipal MyUserDetails myUserDetails,
            PostSave postSave, Model model) {

        String email = myUserDetails.getUsername();
        int rs = postService.postUpdateOk(email,postSave);

        if (rs == 1) {
            System.out.println("수정성공");

            return "redirect:/post/postList";
        } else {
            System.out.println("수정실패");
        }

        return "redirect:/post/postList";

    }

    // update 페이지로 이동
    @GetMapping("/postUpdate/{id}")
    public String postUpdate(@PathVariable("id") Long id, Model model) {

        PostSave postSave1 = postService.postUpdate(id);

        if (postSave1 != null) {
            model.addAttribute("post", postSave1);

            return "post/postUpdate";

        }

        return "redirect:/post/postList";

    }

    @GetMapping("/postDelete/{id}")
    public String postDelete(@PathVariable("id") Long id) {

        postService.postDelete(id);

        return "redirect:/post/postList";

    }

}








