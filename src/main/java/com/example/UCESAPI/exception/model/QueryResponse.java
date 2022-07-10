package com.example.UCESAPI.exception.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "query_responses")
public class QueryResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String body;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    //@JsonBackReference(value = "response-query")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_query")
    private Query query;
}
