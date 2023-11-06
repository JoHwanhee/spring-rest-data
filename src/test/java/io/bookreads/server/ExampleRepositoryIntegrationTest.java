package io.bookreads.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ExampleRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExampleRepository exampleRepository;

    @BeforeEach
    public void setup() {
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setName("Test Name");
        entityManager.persist(exampleEntity);
        entityManager.flush();
    }

    @Test
    void whenFindById_thenReturnExampleEntity() {
        ExampleEntity example = new ExampleEntity();
        example.setName("Test Name");
        example = entityManager.persistAndFlush(example);

        Optional<ExampleEntity> foundEntity = exampleRepository.findById(example.getId());

        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getName()).isEqualTo(example.getName());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Long invalidId = -1L;

        Optional<ExampleEntity> foundEntity = exampleRepository.findById(invalidId);

        assertThat(foundEntity).isNotPresent();
    }
}