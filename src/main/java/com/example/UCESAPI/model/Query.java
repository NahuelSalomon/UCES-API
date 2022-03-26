package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@DiscriminatorValue("1")
public class Query extends Forum{

    @JsonManagedReference(value = "response-query")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "query")
    private List<ResponseQuery> responses;

    @Override
    public ForumType forumType() {
        return ForumType.QUERY;
    }
}
