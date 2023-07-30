package com.colendi.financial.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(schema = "public", name = "user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
  long id;

  @Column(name = "first_name")
  String firstName;

  @Column(name = "last_name")
  String lastName;

  @Builder.Default
  @Column(name = "created_at")
  Timestamp createdAt = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));

  @Column(name = "updated_at")
  Timestamp updatedAt;
}

