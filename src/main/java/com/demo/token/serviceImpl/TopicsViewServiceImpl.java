package com.demo.token.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.token.model.TopicSummaryView;
import com.demo.token.repo.TopicViewRepository;
import com.demo.token.service.TopicsViewService;

@Service
public class TopicsViewServiceImpl implements TopicsViewService {
@Autowired
private TopicViewRepository topicViewRepository;

//@Autowired
//private TopicsRepository topicsRepository;
	@Override
	public List<TopicSummaryView> getAllTopics() {
		//return  topicsRepository.getAlltopics();
		//return  topicViewRepository.findAll();
		return topicViewRepository.getAlltopics();
	}
}
        
	
