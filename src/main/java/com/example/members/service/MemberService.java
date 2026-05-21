package com.example.members.service;

import com.example.members.domain.Member;
import com.example.members.dto.MemberRequest;
import com.example.members.dto.MemberResponse;
import com.example.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    public MemberResponse saveMember(MemberRequest request) {
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

    public MemberResponse uploadProfileImage(Long id, MultipartFile file) throws IOException {
        log.info("[API - LOG] POST /api/members/{}/profile-image 요청", id);

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[API - LOG] Member not found - id: {}", id);
                    return new IllegalArgumentException("해당 멤버가 없습니다. id: " + id);
                });

        String fileName = s3Service.uploadFile(file);
        member.updateProfileImageUrl(fileName);
        memberRepository.save(member);

        return new MemberResponse(member);
    }

    public String getPresignedUrl(Long id) {
        log.info("[API - LOG] GET /api/members/{}/profile-image 요청", id);

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[API - LOG] Member not found - id: {}", id);
                    return new IllegalArgumentException("해당 멤버가 없습니다. id: " + id);
                });

        return s3Service.generatePresignedUrl(member.getProfileImageUrl());
    }
}
