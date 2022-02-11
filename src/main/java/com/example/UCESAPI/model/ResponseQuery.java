package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.querydsl.QuerydslRepositoryInvokerAdapter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "responses_query")
public class ResponseQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference(value = "response-query")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id")
    private Query query;
}
