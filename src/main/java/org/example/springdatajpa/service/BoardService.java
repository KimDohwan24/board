package org.example.springdatajpa.service;

import lombok.RequiredArgsConstructor;
import org.example.springdatajpa.dto.BoardResponseDto;
import org.example.springdatajpa.dto.BoardWithAgeResponseDto;
import org.example.springdatajpa.entity.Board;
import org.example.springdatajpa.entity.Member;
import org.example.springdatajpa.repository.BoardRepository;
import org.example.springdatajpa.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;

    private  final BoardRepository boardRepository;

    // 게시글 생성 API
    public BoardResponseDto save(String title, String contents, String username) {

        Member findMember = memberRepository.findMemberByUsernameOrElseThrow(username);

        Board board = new Board(title, contents);
        board.setMember(findMember);

        Board savedBoard = boardRepository.save(board);

        return new BoardResponseDto(savedBoard.getId(),savedBoard.getTitle(),savedBoard.getContents());
    }

    // 게시판 전체 조회 API
    public List<BoardResponseDto> findAll() {

        return boardRepository.findAll()
                .stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    // 특정 게시물 조회 API
    public BoardWithAgeResponseDto findById(Long id) {

        Board findBoard = boardRepository.findByIdOrElseThrow(id);
        Member writer = findBoard.getMember();

        return new BoardWithAgeResponseDto(findBoard.getTitle(), findBoard.getContents(), writer.getAge());
    }

    // 게시물 삭제 API
    public void delete(Long id) {
        Board findBoard = boardRepository.findByIdOrElseThrow(id);
        boardRepository.delete(findBoard);
    }
}
