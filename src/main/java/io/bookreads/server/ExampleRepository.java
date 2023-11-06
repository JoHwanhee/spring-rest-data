package io.bookreads.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "examples", path = "examples")
public interface ExampleRepository extends JpaRepository<ExampleEntity, Long> { }