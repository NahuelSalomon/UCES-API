package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.model.*;
import com.example.UCESAPI.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final BoardService boardService;

    @Autowired
    public ForumService(ForumRepository forumRepository,BoardService boardService){
        this.forumRepository = forumRepository;
        this.boardService = boardService;
    }

    public Forum addForum(Forum forum) {
        return this.forumRepository.save(forum);
    }

    public Page<Forum> getAll(Pageable pageable) {
        return forumRepository.findAll(pageable);
    }

    public Page<Query> getAllQueriesByBoard(int idBoard, Pageable pageable) throws BoardNotFoundException {
        Board board = boardService.getById(idBoard);
        return this.forumRepository.findAllQueriesByBoard(board,pageable);
    }

    public Page<Query> getAllQueriesByBoardSortedByVotes(int idBoard, Pageable pageable) throws BoardNotFoundException {
        Board board = boardService.getById(idBoard);
        return this.forumRepository.findAllQueriesOrderByUsersWhoVotedCountDesc(board.getId(),pageable);
    }

    public Page<Query> getAllQueriesByBoardSortedByDate(int idBoard, Pageable pageable) throws BoardNotFoundException {
        Board board = boardService.getById(idBoard);
        return this.forumRepository.findAllQueriesByBoardOrderByDateDesc(board,pageable);
    }

    public Page<Recommendation> getAllRecommendationsByBoard(int idBoard, Pageable pageable) throws BoardNotFoundException {
        Board board = boardService.getById(idBoard);
        return this.forumRepository.findAllRecommendationsByBoard(board,pageable);
    }

    public Page<Recommendation> getAllRecommendationsByBoardSortedByVotes(int idBoard, Pageable pageable) throws BoardNotFoundException {
        Board board = boardService.getById(idBoard);
        return this.forumRepository.findAllRecommendationsOrderByUsersWhoVotedCountDesc(board.getId(),pageable);
    }

    public Page<Recommendation> getAllRecommendationsByBoardSortedByDate(int idBoard, Pageable pageable) throws BoardNotFoundException {
        Board board = boardService.getById(idBoard);
        return this.forumRepository.findAllRecommendationsByBoardOrderByDateDesc(board,pageable);
    }

    public Forum getById(Integer id) throws ForumNotFoundException {
        return forumRepository.findById(id)
                .orElseThrow(ForumNotFoundException::new);
    }

    public void deleteById(Integer id) throws ForumNotFoundException {
        if(forumRepository.existsById(id)){
            forumRepository.deleteById(id);
        }else{
            throw new ForumNotFoundException();
        }
    }

    public void update(Integer id, Forum newForum) throws ForumNotFoundException {
        Forum f = getById(id);
        f.setBody(newForum.getBody());
        f.setUser(newForum.getUser());
        forumRepository.save(f);
    }
}
