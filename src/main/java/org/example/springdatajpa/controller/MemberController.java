package org.example.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import org.example.springdatajpa.dto.MemberResponseDto;
import org.example.springdatajpa.dto.SignUpRequestDto;
import org.example.springdatajpa.dto.SignUpResponseDto;
import org.example.springdatajpa.dto.UpdatePasswordRequestDto;
import org.example.springdatajpa.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto = memberService.signUp(requestDto.getUsername(), requestDto.getPassword(), requestDto.getAge());

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    // 특정 회원 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {

        MemberResponseDto memberResponseDto = memberService.findById(id);

        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    // 비밀번호 변경 API
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody UpdatePasswordRequestDto requestDto
    ) {

        memberService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
