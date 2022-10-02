package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("1")
public class Query extends Forum{

    @JsonManagedReference(value = "response-query")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "query")
    private List<QueryResponse> responses;

    @Override
    public ForumType forumType() {
        return ForumType.QUERY;
    }
}
