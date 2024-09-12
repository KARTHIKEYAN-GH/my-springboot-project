package com.demo.token.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.token.model.TopicSummaryView;
import com.demo.token.service.TopicsViewService;

@RestController 
@RequestMapping("/api/topic-view")
public class TopicViewController {

	@Autowired
	private TopicsViewService topicsViewService;
	
	@GetMapping
    public List<TopicSummaryView> getAllTopics() {
        return topicsViewService.getAllTopics();
    }
}
