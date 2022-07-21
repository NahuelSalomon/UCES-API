package com.example.UCESAPI.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Builder
@Entity(name = "queries")
//@DiscriminatorValue("1")
public class Query extends Forum{

    /*@JsonManagedReference(value = "response-query")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "query")
    private List<QueryResponse> responses;*/

    @Override
    public ForumType forumType() {
        return ForumType.QUERY;
    }
}
