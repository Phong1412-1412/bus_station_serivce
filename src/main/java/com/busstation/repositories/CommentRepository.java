package com.busstation.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.busstation.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
		
	@Query("SELECT c FROM Comment c WHERE c.trip.tripId = ?1 AND c.rating >= ?2")
	Page<Comment> findByTripIdAndRatingGreaterThanEqual(String tripId, int rating, Pageable pageable);
}