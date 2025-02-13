package com.tree.controller;

import com.tree.dto.LeaderboardDTO;
import com.tree.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    // ✅ API to fetch top 100 tree owners based on total rewards
    @GetMapping("/top")
    public ResponseEntity<List<LeaderboardDTO>> getTopTreeOwners() {
        List<LeaderboardDTO> leaderboard = leaderboardService.getLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }
}
