package com.example.members.controller;

import com.example.members.dto.MemberRequest;
import com.example.members.dto.MemberResponse;
import com.example.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
