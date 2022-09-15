package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "queries")
//@DiscriminatorValue("1")
public class Query extends Forum{

    @JsonManagedReference(value = "response-query")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "query")
    private List<QueryResponse> responses;

    @Override
    @AccessType(AccessType.Type.FIELD)
    public ForumType forumType() {
        return ForumType.QUERY;
    }
}
