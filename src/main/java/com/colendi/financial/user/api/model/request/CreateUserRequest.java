package com.colendi.financial.user.api.model.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserRequest {

  String firstName;
  String lastName;
}
