package io.bookreads.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ExampleRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExampleRepository sut;

    @BeforeEach
    public void setup() {
        var exampleEntity = exampleEntity();
        entityManager.persist(exampleEntity);
        entityManager.flush();
    }

    @Test
    void whenFindById_thenReturnExampleEntity() {
        var example = exampleEntity();
        example = entityManager.persistAndFlush(example);

        var actual = sut.findById(example.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get().getName()).isEqualTo(example.getName());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        var actual = sut.findById(
                invalidId(-1L)
        );

        assertThat(actual).isNotPresent();
    }

    private static Long invalidId(Long id) {
        return id;
    }

    private static ExampleEntity exampleEntity() {
        ExampleEntity example = new ExampleEntity();
        example.setName("Test Name");
        return example;
    }
}