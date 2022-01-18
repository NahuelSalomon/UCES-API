package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Query extends Forum{

    @OneToMany(cascade = CascadeType.ALL)
    private List<ResponseQuery> responses;

    @Override
    public ForumType forumType() {
        return ForumType.QUERY;
    }
}
