package com.tree.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tree_donation_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeDonationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private TreeOwner requester;

    @ManyToOne
    @JoinColumn(name = "donator_id", nullable = false)
    private TreeDonation donation;

    @Column(nullable = false)
    private int requestedQuantity;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;
}
