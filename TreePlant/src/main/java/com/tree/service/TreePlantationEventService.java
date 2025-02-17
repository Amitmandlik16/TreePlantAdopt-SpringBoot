package com.tree.service;

import com.tree.entity.TreeOwner;
import com.tree.entity.TreePlantationEvent;
import com.tree.repository.TreeOwnerepo;
import com.tree.repository.TreePlantationEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TreePlantationEventService {

	@Autowired
	private TreePlantationEventRepository eventRepository;

	@Autowired
	private TreeOwnerepo treeOwnerRepository;

	@Autowired
	private AdminEmailService adminEmailService;

	// ✅ Create a new event and send email notifications
	@Transactional
	public TreePlantationEvent createEvent(TreePlantationEvent event) {
		TreePlantationEvent savedEvent = eventRepository.save(event);

		// 📧 Send email notification
		String subject = "🌱 New Tree Plantation Drive Event!";
		String messageContent = "A new Tree Plantation Drive Event has been scheduled!<br><br>"
				+ "<strong>Event Details:</strong><br>" + "📍 Location: " + event.getPlace() + "<br>" + "📅 Date: "
				+ event.getDate() + "<br>" + "⏰ Time: " + event.getTime() + "<br><br>"
				+ "Join the event by logging into your account on <a href='https://www.treeplantadopt.com'>TreePlantAdopt Website</a>.";

		adminEmailService.sendHtmlEmailsToAllTreeOwners(subject, messageContent);

		return savedEvent;
	}

	// ✅ Get all events
	public List<TreePlantationEvent> getAllEvents() {
		return eventRepository.findAll();
	}

	// ✅ Admin can delete an event
	@Transactional
	public void deleteEvent(Long eventId) {
		eventRepository.deleteById(eventId);
	}

	// ✅ Join an event
	@Transactional
	public String joinEvent(Long eventId, Long treeOwnerId) {
		Optional<TreePlantationEvent> eventOptional = eventRepository.findById(eventId);
		Optional<TreeOwner> treeOwnerOptional = treeOwnerRepository.findById(treeOwnerId);

		if (eventOptional.isPresent() && treeOwnerOptional.isPresent()) {
			TreePlantationEvent event = eventOptional.get();
			TreeOwner treeOwner = treeOwnerOptional.get();
			event.getParticipants().add(treeOwner);
			eventRepository.save(event);
			return "Joined event successfully!";
		}
		return "Event or Tree Owner not found!";
	}

	// ✅ Leave an event
	@Transactional
	public String leaveEvent(Long eventId, Long treeOwnerId) {
		Optional<TreePlantationEvent> eventOptional = eventRepository.findById(eventId);
		Optional<TreeOwner> treeOwnerOptional = treeOwnerRepository.findById(treeOwnerId);

		if (eventOptional.isPresent() && treeOwnerOptional.isPresent()) {
			TreePlantationEvent event = eventOptional.get();
			TreeOwner treeOwner = treeOwnerOptional.get();
			if (event.getParticipants().contains(treeOwner)) {
				event.getParticipants().remove(treeOwner);
				eventRepository.save(event);
				return "Left event successfully!";
			}
			return "You are not registered for this event!";
		}
		return "Event or Tree Owner not found!";
	}
}
