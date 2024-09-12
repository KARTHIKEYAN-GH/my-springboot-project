package com.demo.token.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.token.model.TopicsReadStatus;

public interface TopicReadStatusRepository extends JpaRepository<TopicsReadStatus, Long>{
	
	/**
	 * To find a users by uuid
	 * @param usersUuid
	 * @return TopicsReadStatus
	 */
    Optional<TopicsReadStatus> findByUsersUuid(String usersUuid);
    
    /**
     * To find a topics uuid
     * @param topicsUuid
     * @return TopicsReadStatus
     */
    Optional<TopicsReadStatus> findByTopicsUuid(String topicsUuid);


    Optional<TopicsReadStatus> findByTopicsUuidAndUsersUuid(String topicsUuid, String usersUuid);

}
