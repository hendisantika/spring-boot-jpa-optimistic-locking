package com.hendisantika.springbootjpaoptimisticlocking;

import com.hendisantika.springbootjpaoptimisticlocking.domain.Movie;
import com.hendisantika.springbootjpaoptimisticlocking.dto.MovieDTO;
import com.hendisantika.springbootjpaoptimisticlocking.dto.Movies;
import com.hendisantika.springbootjpaoptimisticlocking.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SpringBootJpaOptimisticLockingApplicationTests {

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
        movieRepository.save(new Movie("The great movie", 5));
        movieRepository.save(new Movie("The not so great movie", 4));
        movieRepository.save(new Movie("Yet another movie", 3));
        movieRepository.save(new Movie("The big crap", 1));
    }

    /**
     * Tests the situation, that there are two concurrent users working with a web application
     * and both operating on the same data at the same time.
     */
    @Test
    void testConcurrencyWriting() {
        assertEquals(4, movieRepository.count(), "Number of movies in Database incorrect.");

        Movie theGreatMovieForUserOne = movieRepository.findByTitle("The great movie");
        Movie theGreatMovieForUserTwo = movieRepository.findByTitle("The great movie");

        // At first, serialize the movies to transport them to a view.
        MovieDTO theGreatMovieDTOForUserOne = Movies.of(theGreatMovieForUserOne);
        MovieDTO theGreatMovieDTOForUserTwo = Movies.of(theGreatMovieForUserTwo);

        // The view modifies the transport objects.
        theGreatMovieDTOForUserOne.setRating(1);
        theGreatMovieDTOForUserTwo.setRating(0);

        // The view sends the transport objects to the backend.
        Movie theUpdatedGreatMovieForUserOne = Movies.of(theGreatMovieDTOForUserOne);
        Movie theUpdatedGreatMovieForUserTwo = Movies.of(theGreatMovieDTOForUserTwo);

        // The versions of the updated movies are both 0.
        assertEquals(0, theUpdatedGreatMovieForUserOne.getVersion().intValue());
        assertEquals(0, theUpdatedGreatMovieForUserTwo.getVersion().intValue());

        // The backend tries to save both.
        movieRepository.save(theUpdatedGreatMovieForUserOne);

        // OUTCH! Exception! The second save works on a stale version.
        assertThrows(ObjectOptimisticLockingFailureException.class,
                () -> movieRepository.save(theUpdatedGreatMovieForUserTwo));
    }
}
