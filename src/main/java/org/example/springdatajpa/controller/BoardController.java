package org.example.springdatajpa.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.springdatajpa.dto.BoardResponseDto;
import org.example.springdatajpa.dto.BoardWithAgeResponseDto;
import org.example.springdatajpa.dto.CreateBoardRequestDto;
import org.example.springdatajpa.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시판 생성 API
    @PostMapping
    public ResponseEntity<BoardResponseDto> save(@RequestBody CreateBoardRequestDto requestDto) {

        BoardResponseDto boardResponseDto = boardService.save(requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername());

        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }

    // 게시물 전체 조회 API
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> findAll() {
        List<BoardResponseDto> boardResponseDtoList = boardService.findAll();

        return new ResponseEntity<>(boardResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardWithAgeResponseDto> findById(
            @PathVariable Long id
    ) {
        BoardWithAgeResponseDto boardWithAgeResponseDto = boardService.findById(id);

        return new ResponseEntity<>(boardWithAgeResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boardService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
