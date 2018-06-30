package com.platzi.amazonviewer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.platzi.amazonviewer.db.IDBConnection;
import com.platzi.amazonviewer.model.Movie;
import static com.platzi.amazonviewer.db.DataBase.*;

public interface MovieDAO extends IDBConnection {
	
	@SuppressWarnings("finally")
	default Movie setMovieViewed(Movie movie) {
		try (Connection connection = connectToDB()) {
			Statement statement = connection.createStatement();
			String query = "INSERT INTO " + TVIEWED + 
					" (" + TVIEWED_IDMATERIAL + ", " + TVIEWED_IDELEMENT + ", " + TVIEWED_IDUSUARIO + ")" +
					" VALUES (" + TMATERIALS_ID[0] + ", " + movie.getId() + ", " + TUSER_IDUSUARIO + ")";
			if (statement.executeUpdate(query) > 0) System.out.println("Se marcó en visto");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return movie;
		}
	}
	
	@SuppressWarnings("finally")
	default ArrayList<Movie> read() {
		ArrayList<Movie> movies = new ArrayList<>();
		
		try (Connection connection = connectToDB()) {
			String query = "SELECT * FROM " + TMOVIE + ";";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Movie movie = new Movie(
						resultSet.getString(TMOVIE_TITLE),
						resultSet.getString(TMOVIE_GENRE),
						resultSet.getString(TMOVIE_CREATOR),
						resultSet.getInt(TMOVIE_DURATION),
						resultSet.getShort(TMOVIE_YEAR));
				
				movie.setId(resultSet.getInt(TMOVIE_ID));
				movie.setViewed(this.getMovieViewed(preparedStatement, connection, movie.getId()));
				movies.add(movie);
			}
			
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return movies;
		}
	}
	
	@SuppressWarnings("finally")
	private boolean getMovieViewed(PreparedStatement preparedStatement, Connection connection, int idMovie) {
		boolean viewed = false;
		String query = "SELECT * FROM " + TVIEWED +
				" WHERE " + TVIEWED_IDMATERIAL + " = ?" +
				" AND " + TVIEWED_IDELEMENT + " = ?" +
				" AND " + TVIEWED_IDUSUARIO + " = ?;";
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, TMATERIALS_ID[0]);
			preparedStatement.setInt(2, idMovie);
			preparedStatement.setInt(3, TUSER_IDUSUARIO);
			
			resultSet = preparedStatement.executeQuery();
			viewed = resultSet.next();
			
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return viewed;
		}
	}
}