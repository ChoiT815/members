package com.example.members.service;

import com.example.members.domain.Member;
import com.example.members.dto.MemberRequest;
import com.example.members.dto.MemberResponse;
import com.example.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse save(MemberRequest request) {
        log.info("[API - LOG] POST /api/members 요청 - name: {}, age: {}, mbti: {}", request.getName(), request.getAge(), request.getMbti());
        Member member = new Member(request.getName(), request.getAge(), request.getMbti());
        memberRepository.save(member);
        return new MemberResponse(member);
    }

    public MemberResponse findById(Long id) {
        log.info("[API - LOG] GET /api/members/{} 요청", id);

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[API - LOG] Member not found - id: {}", id);
                    return new IllegalArgumentException("해당 멤버가 없습니다. id: " + id);
                });
        return new MemberResponse(member);
    }
}
