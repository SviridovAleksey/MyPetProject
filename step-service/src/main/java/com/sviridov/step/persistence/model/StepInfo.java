package com.sviridov.step.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "steps_info")
@NoArgsConstructor
public class StepInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "step_place")
    private Long stepPlace;

    @Column(name = "instruction_info_id")
    private Long infoId;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "text_id")
    private StepText stepText;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}
