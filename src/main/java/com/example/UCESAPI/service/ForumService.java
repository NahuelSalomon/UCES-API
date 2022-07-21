package com.example.UCESAPI.service;

import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.ForumType;
import com.example.UCESAPI.model.QueryResponse;
import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final BoardService boardService;

    @Autowired
    public ForumService(ForumRepository forumRepository, BoardService boardService){
        this.forumRepository = forumRepository;
        this.boardService = boardService;
    }

    public Forum addForum(Forum forum) {
        return this.forumRepository.save(forum);
    }

    public Page<Forum> getAll(Pageable pageable) {
        return forumRepository.findAll(pageable);
    }
    public List<Forum> getAllByBoard(int idBoard) throws BoardNotFoundException {
        Board board = boardService.getById(idBoard);
        return forumRepository.findAllByBoard(board);
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
        f.setDownVotes(newForum.getDownVotes());
        f.setUpVotes(newForum.getUpVotes());
        if (f.forumType() == ForumType.QUERY){
            //((Query)(f)).setResponses(((Query)(newForum)).getResponses());
        }
        forumRepository.save(f);
    }

    public void addResponse(Integer id, QueryResponse response) throws ForumNotFoundException {
        Forum f = getById(id);
        if (f.forumType() == ForumType.QUERY){


            /*List<QueryResponse> responses = ((Query)(f)).getResponses();
            responses.add(response);
            ((Query)(f)).setResponses(responses);*/
            forumRepository.save(f);
        }
    }

    public void deleteResponse(Integer idForum, Integer idResponse) throws ForumNotFoundException {
        Forum f = getById(idForum);
        if (f.forumType() == ForumType.QUERY){

            /*List<QueryResponse> responses = ((Query)(f)).getResponses();
            responses.removeIf(r -> r.getId().equals(idResponse));
            ((Query)(f)).setResponses(responses);*/
            forumRepository.save(f);
        }
    }
}
