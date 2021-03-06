package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.service.BoardService;
import com.example.UCESAPI.utils.EntityURLBuilder;
import com.example.UCESAPI.utils.ResponseEntityMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping
    public ResponseEntity<Board> addBoard(@RequestBody Board board){
        Board newBoard = boardService.addBoard(board);
        return ResponseEntity.created(EntityURLBuilder.buildURL(BOARD_PATH, newBoard.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAll(Pageable pageable){
        Page page = boardService.getAll(pageable);
        return ResponseEntityMaker.response(page.getContent(), page);
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBoard(@PathVariable Integer id) throws BoardNotFoundException {
        boardService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBoard(@PathVariable Integer id, @RequestBody Board newBoard) throws BoardNotFoundException {
        boardService.update(id, newBoard);
        return ResponseEntity.accepted().build();
    }



}
