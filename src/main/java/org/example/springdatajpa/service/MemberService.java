package org.example.springdatajpa.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.example.springdatajpa.dto.MemberResponseDto;
import org.example.springdatajpa.dto.SignUpResponseDto;
import org.example.springdatajpa.entity.Member;
import org.example.springdatajpa.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private  final MemberRepository memberRepository;

    // 회원가입 API
    public SignUpResponseDto signUp(String username, String password, Integer age) {

        Member member = new Member(username, password, age);

        Member savedMember = memberRepository.save(member);

        return new SignUpResponseDto(savedMember.getId(), savedMember.getPassword(),savedMember.getAge());
    }


    // 특정 유저 조회하기 API
    public MemberResponseDto findById(Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);

        if(optionalMember.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exixts id : " + id);
        }

        Member findMember = optionalMember.get();

        return new MemberResponseDto(findMember.getUsername(),findMember.getAge());
    }

    // 비밀번호 변경 API
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        if(!findMember.getPassword().equals(oldPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");
        }

        findMember.updatePassword(newPassword);

    }
}
