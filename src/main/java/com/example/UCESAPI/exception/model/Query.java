package com.example.UCESAPI.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.OverridesAttribute;

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
