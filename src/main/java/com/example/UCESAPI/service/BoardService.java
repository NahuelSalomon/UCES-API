package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.BoardNotExistsException;
import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public Board addBoard(Board board) {
        return this.boardRepository.save(board);
    }

    public Page<Board> getAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board getById(Integer id) throws BoardNotExistsException {
        return boardRepository.findById(id)
                .orElseThrow(BoardNotExistsException::new);
    }

    public void deleteById(Integer id) throws BoardNotExistsException {
        if(boardRepository.existsById(id)){
            boardRepository.deleteById(id);
        }else{
            throw new BoardNotExistsException();
        }
    }

    public void update(Integer id, Board newBoard) throws BoardNotExistsException {
        Board b = getById(id);
        b.setName(newBoard.getName());
        b.setForumList(newBoard.getForumList());
        boardRepository.save(b);
    }
}
