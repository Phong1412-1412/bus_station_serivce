package com.busstation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.busstation.entities.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String>{

	@Query("Select r from Rating r where r.user.userId=?1")
	Rating findRatingByUserId(String userId);

}
