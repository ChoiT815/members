package com.example.members.controller;

import com.example.members.dto.MemberRequest;
import com.example.members.dto.MemberResponse;
import com.example.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> saveMember(@RequestBody MemberRequest request) {
        log.info("[API - LOG] POST /api/members");
        MemberResponse response = memberService.saveMember(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberfindById(@PathVariable Long id) {
        log.info("[API - LOG] GET /api/members/{}", id);
        MemberResponse response = memberService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/profile-image")
    public ResponseEntity<MemberResponse> updateProfileImageUrl(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        log.info("[API - LOG] POST /api/members/{}/profile-image", id);
        MemberResponse response = memberService.uploadProfileImage(id, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/profile-image")
    public ResponseEntity<String> getProfileImageUrl(@PathVariable Long id) {
        log.info("/api/members/{}/profile-image", id);
        String presignedUrl = memberService.getPresignedUrl(id);
        return ResponseEntity.ok(presignedUrl);
    }
}
