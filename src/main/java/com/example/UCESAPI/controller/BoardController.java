package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/boards")
public class BoardController {

    final private BoardService boardService;
    final private String BOARD_PATH = "/api/board";

    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable Integer id) throws BoardNotFoundException {
        Board board = boardService.getById(id);
        return ResponseEntity.ok(board);
    }

    @GetMapping("/subjects/{idSubject}")
    public ResponseEntity<Board> getBySubject(@PathVariable Integer idSubject) throws SubjectNotFoundException {
        Board board = boardService.getBySubject(idSubject);
        return ResponseEntity.ok(board);
    }
}
