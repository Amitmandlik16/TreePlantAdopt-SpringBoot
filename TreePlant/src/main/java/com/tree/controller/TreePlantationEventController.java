package com.tree.controller;

import com.tree.entity.TreePlantationEvent;
import com.tree.service.TreePlantationEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/events")
public class TreePlantationEventController {

	@Autowired
	private TreePlantationEventService eventService;

	// ✅ Create a new event (Admin)
	@PostMapping("/create")
	public ResponseEntity<TreePlantationEvent> createEvent(@RequestBody TreePlantationEvent event) {
		return ResponseEntity.ok(eventService.createEvent(event));
	}

	// ✅ Get all events
	@GetMapping("/all")
	public ResponseEntity<List<TreePlantationEvent>> getAllEvents() {
		return ResponseEntity.ok(eventService.getAllEvents());
	}

	// ✅ Delete an event (Admin)
	@DeleteMapping("/delete/{eventId}")
	public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
		eventService.deleteEvent(eventId);
		return ResponseEntity.ok("Event deleted successfully!");
	}

	// ✅ Join an event (Tree Owner)
	@PostMapping("/{eventId}/join/{treeOwnerId}")
	public ResponseEntity<String> joinEvent(@PathVariable Long eventId, @PathVariable Long treeOwnerId) {
		return ResponseEntity.ok(eventService.joinEvent(eventId, treeOwnerId));
	}

	// ✅ Leave an event (Tree Owner)
	@PostMapping("/{eventId}/leave/{treeOwnerId}")
	public ResponseEntity<String> leaveEvent(@PathVariable Long eventId, @PathVariable Long treeOwnerId) {
		return ResponseEntity.ok(eventService.leaveEvent(eventId, treeOwnerId));
	}
}
