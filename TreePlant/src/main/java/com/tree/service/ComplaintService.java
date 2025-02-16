package com.tree.service;

import com.tree.entity.Complaint;
import com.tree.repository.ComplaintRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintService {

	@Autowired
	private ComplaintRepository complaintRepository;

	public Complaint submitComplaint(Complaint complaint) {
		complaint.setLocalDate(LocalDate.now());
		complaint.setLocalTime(LocalTime.now());
		return complaintRepository.save(complaint);
	}

	public List<Complaint> getAllComplaints() {
		return complaintRepository.findAll();
	}
}
