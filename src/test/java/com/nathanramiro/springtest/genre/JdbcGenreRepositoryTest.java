package com.nathanramiro.springtest.genre;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import com.nathanramiro.springtest.bookindex.BookIndex;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JdbcGenreRepository.class)
@ActiveProfiles("test")
public class JdbcGenreRepositoryTest {

    @Autowired
    JdbcGenreRepository repository;

    final BookIndex bookIndex = new BookIndex(null,
            "indexNameTest",
            "0000000000000",
            "indexWriterTest",
            "indexPublisherTest");

    @Test
    void testGetAll() {

        List<String> result = repository.getAll();

        assertTrue(result.size() > 0);
    }

    @Test
    void testPostAddIndexToGenre() {

        repository.postAddIndexToGenre(new IndexGenreComp(bookIndex, List.of("genreName")));
    }

    @Test
    void testPostNewGenre() {

        repository.postNewGenre(List.of(new Genre(null, "unitTest3")));
    }
}
