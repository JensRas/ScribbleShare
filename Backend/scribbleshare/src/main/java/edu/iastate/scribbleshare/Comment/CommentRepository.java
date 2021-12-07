package edu.iastate.scribbleshare.Comment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends CrudRepository<Comment, Integer>{

    @Query(value = "SELECT count(*) FROM liked_comments lc where lc.comment_id=:comment_id AND lc.username=:username", nativeQuery = true)
    public int getCommentLikedByUser(@Param("comment_id") int comment_id, @Param("username") String username);

}