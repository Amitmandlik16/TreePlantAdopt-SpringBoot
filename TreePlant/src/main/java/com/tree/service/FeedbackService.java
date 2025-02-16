package com.tree.service;

import com.tree.entity.Feedback;
import com.tree.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	public Feedback saveFeedback(Feedback feedback) {
		feedback.setLocalDate(LocalDate.now());
		feedback.setLocalTime(LocalTime.now());
		return feedbackRepository.save(feedback);
	}

	public List<Feedback> getAllFeedbacks() {
		return feedbackRepository.findAll();
	}
}
