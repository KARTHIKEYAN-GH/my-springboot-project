package com.demo.token.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.token.model.TopicsReadStatus;
import com.demo.token.repo.TopicReadStatusRepository;
import com.demo.token.service.TopicsReadStatusService;

@Service
public class TopicsReadStatusImpl implements TopicsReadStatusService {

	private final TopicReadStatusRepository topicReadStatusRepository;

	public TopicsReadStatusImpl(TopicReadStatusRepository topicReadStatusRepository) {
		super();
		this.topicReadStatusRepository = topicReadStatusRepository;
	}

	@Override
	public TopicsReadStatus saveStatus(TopicsReadStatus topicsReadStatus) {
		return topicReadStatusRepository.save(topicsReadStatus);
	}

	@Override
	public Optional<TopicsReadStatus> getReadStatusByUsersUuid(String usersUuid) {
		// TODO Auto-generated method stub
		return topicReadStatusRepository.findByUsersUuid(usersUuid);
	}

//	@Override
//	public Optional<TopicsReadStatus> getReadStatusByTopicsUuid(String topicsUuid) {
//		// TODO Auto-generated method stub
//		return topicReadStatusRepository.findByTopicsUuid(topicsUuid);
//	}

	@Override
	public Optional<TopicsReadStatus> getReadStatusByTopicsUuidAndUserUuid(String topicsUuid, String usersUuid) {
		// TODO Auto-generated method stub
		return topicReadStatusRepository.findByTopicsUuidAndUsersUuid(topicsUuid, usersUuid);
	}

}
