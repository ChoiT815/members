package com.example.members.dto;

import com.example.members.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private Long id;
    private String name;
    private int age;
    private String mbti;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.age = member.getAge();
        this.mbti = member.getMbti();
    }
}
