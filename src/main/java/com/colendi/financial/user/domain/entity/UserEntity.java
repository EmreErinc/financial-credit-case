package com.colendi.financial.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(schema = "public", name = "user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
  long id;

  @Column(name = "first_name")
  String firstName;

  @Column(name = "last_name")
  String lastName;

  @Column(name = "created_at")
  Timestamp createdAt;

  @Column(name = "updated_at")
  Timestamp updatedAt;
}

