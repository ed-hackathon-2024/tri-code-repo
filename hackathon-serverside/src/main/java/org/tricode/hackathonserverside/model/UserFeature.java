package org.tricode.hackathonserverside.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_features")
public class UserFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String question;
    @Column(nullable = false)
    private String answer;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
