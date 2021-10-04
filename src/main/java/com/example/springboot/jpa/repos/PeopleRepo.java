package com.example.springboot.jpa.repos;

import com.example.springboot.jpa.entities.People;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PeopleRepo extends PagingAndSortingRepository<People, Long> {

}
