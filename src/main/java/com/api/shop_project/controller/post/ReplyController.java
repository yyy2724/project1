package com.api.shop_project.controller.post;

import com.api.shop_project.config.MyUserDetails;
import com.api.shop_project.domain.post.Post;
import com.api.shop_project.domain.post.Reply;
import com.api.shop_project.dto.request.reply.ReplySave;
import com.api.shop_project.dto.response.post.PostSave;
import com.api.shop_project.service.post.PostService;
import com.api.shop_project.service.post.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final PostService postService;
    private final ReplyService replyService;



    @PostMapping("/write")
    public String replyWrite(@AuthenticationPrincipal MyUserDetails myUserDetails,
                             @RequestParam Long postId,
                             @RequestParam String content){

        String email = myUserDetails.getUsername();
        replyService.replyInsert(postId, content, email);

        return "redirect:/post/postDetail/" + postId;

    }

    @PostMapping("/update")
    public String replyUpdate(@AuthenticationPrincipal MyUserDetails myUserDetails,
                            @Valid @RequestParam Long id,
                              @RequestParam Long memberId,
                              @RequestParam Long postId,
                              @RequestParam String content){

        String email = myUserDetails.getUsername();
        replyService.replyUpdateOk(email, id, memberId, postId, content);

        return "redirect:/post/postDetail/" + postId;

    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {

        ReplySave replySave = replyService.replyUpdate(id);

        if (replySave != null) {
            model.addAttribute("replySave", replySave);
            return "reply/replyUpdate";
        }
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String replyDelete(@PathVariable("id") Long id,
                              Model model){

        Reply reply = replyService.replyDelete(id);

        Long postId = reply.getPost().getId();

        return "redirect:/post/postDetail/" + postId;
    }




}


