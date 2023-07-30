package com.colendi.financial.user.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDetailResponse {

  long userId;
  String firstName;
  String lastName;
}
