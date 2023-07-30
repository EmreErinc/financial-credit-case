package com.colendi.financial.user.api.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserListResponse {

  List<UserDetailResponse> users;
}
