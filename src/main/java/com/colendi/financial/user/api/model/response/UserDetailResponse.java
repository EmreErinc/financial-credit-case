package com.colendi.financial.user.api.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailResponse {

  long userId;
  String firstName;
  String lastName;
}