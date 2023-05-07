package com.busstation.repositories;

import com.busstation.payload.response.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.busstation.entities.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
		
	@Query("SELECT c FROM Comment c WHERE c.trip.tripId = ?1")
	Page<Comment> findCommentByTripId(String tripId, Pageable pageable);

	@Query("SELECT com from Comment com inner join Trip t on com.trip.tripId = t.tripId where t.tripId = :tripId")
	List<CommentResponse>findByTrip_TripId(@Param("tripId") String tripId);
}