package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.model.Subject;
import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.model.Board;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final SubjectService subjectService;

    @Autowired
    public BoardService(BoardRepository boardRepository, SubjectService subjectService){
        this.boardRepository = boardRepository;
        this.subjectService = subjectService;
    }

    public Board addBoard(Board board) {
        return this.boardRepository.save(board);
    }

    public Page<Board> getAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board getById(Integer id) throws BoardNotFoundException {
        return boardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
    }

    public Board getBySubject(Integer idSubject) throws SubjectNotFoundException {
        Subject subject = this.subjectService.getById(idSubject);
        return boardRepository.findBySubject(subject);

    }

    public void deleteById(Integer id) throws BoardNotFoundException {
        if(boardRepository.existsById(id)){
            boardRepository.deleteById(id);
        }else{
            throw new BoardNotFoundException();
        }
    }

    public void update(Integer id, Board newBoard) throws BoardNotFoundException {
        Board b = getById(id);
        b.setName(newBoard.getName());
        //b.setForumList(newBoard.getForumList());
        boardRepository.save(b);
    }
}
