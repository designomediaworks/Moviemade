package org.michaelbel.moviemade.mvp.base;

import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.People;
import org.michaelbel.moviemade.rest.model.v3.Trailer;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.rest.model.v3.Review;

import java.util.ArrayList;

public interface MediaModel {

    void startMovie(Movie movie);

    void startTrailers(String title);





    void startMovie(MovieRealm movie);

    void startPerson(Cast person);

    void startPerson(People person);

    void startReview(Review review, Movie movie);

    void startReview(Review review, MovieRealm movie);

    void startGenre(Genre genre);

    void startGenres(ArrayList<Genre> list);

    void startKeyword(Keyword keyword);

    void startCollection(Collection collection);

    void startCompany(Company company);
}
