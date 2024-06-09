package com.dropchat.cinemaxmovie.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dropchat.cinemaxmovie.converter.response.MovieResponse;
import com.dropchat.cinemaxmovie.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByName(String name);

    @Query("select distinct new com.dropchat.cinemaxmovie.converter.response.MovieResponse"
            + "(m.id, m.movieDuration, m.endTime, m.premiereDate, m.description, m.director, m.image, m.heroImage, m.language, m.name, m.trailer, m.isActive) "
            + "from Movie m "
            + "join Schedule s on m.id = s.movie.id "
            + "join Room r on s.room.id = r.id "
            + "join Cinema c on r.cinema.id = c.id "
            + "where c.id = :cinemaId")
    Page<MovieResponse> findAllMovieByCinema(Integer cinemaId, Pageable pageable);

    @Query("select distinct new com.dropchat.cinemaxmovie.converter.response.MovieResponse"
            + "(m.id, m.movieDuration, m.endTime, m.premiereDate, m.description, m.director, m.image, m.heroImage, m.language, m.name, m.trailer, m.isActive) "
            + "from Movie m "
            + "join Schedule s on m.id = s.movie.id "
            + "join Room r on s.room.id = r.id "
            + "where r.id = :roomId")
    Page<MovieResponse> findAllMovieByRoom(Integer roomId, Pageable pageable);

    @Query("select distinct new com.dropchat.cinemaxmovie.converter.response.MovieResponse"
            + "(m.id, m.movieDuration, m.endTime, m.premiereDate, m.description, m.director, m.image, m.heroImage, m.language, m.name, m.trailer, m.isActive) "
            + "from Movie m "
            + "join Schedule s on m.id = s.movie.id "
            + "join Room r on s.room.id = r.id "
            + "join Seat st on st.room.id = r.id "
            + "join SeatStatus ss on ss.id = st.seatStatus.id "
            + "where ss.id = :seatStatusId")
    Page<MovieResponse> findAllMovieBySeatStatus(Integer seatStatusId, Pageable pageable);
}
