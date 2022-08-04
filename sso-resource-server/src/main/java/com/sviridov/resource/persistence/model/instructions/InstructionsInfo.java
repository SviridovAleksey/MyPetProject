package com.sviridov.resource.persistence.model.instructions;


import com.sviridov.resource.persistence.model.users.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "instruction_info")
@NoArgsConstructor
public class InstructionsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_creator")
    private User user;

    @Column(name = "description")
    private String description;

    @Column(name = "level_id")
    private Long levelOwner;

    @ManyToOne
    @JoinColumn(name = "construct_id")
    private InstructionConstruct instructionConstruct;

    @Column(name = "is_delete")
    private boolean isDelete;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
