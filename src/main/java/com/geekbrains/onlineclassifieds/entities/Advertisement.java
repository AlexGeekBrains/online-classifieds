package com.geekbrains.onlineclassifieds.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "user_price")
    private BigDecimal userPrice;

    @NonNull
    @Column(name = "is_paid")
    private Boolean isPaid;

    @NonNull
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @NonNull
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "advertisement")
    private Collection<Payment> payments;

//    @NonNull ToDo: decide how to work with categories
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}