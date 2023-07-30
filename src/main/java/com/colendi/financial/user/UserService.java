package com.colendi.financial.user;

import com.colendi.financial.commons.DoneResponse;
import com.colendi.financial.user.api.model.request.CreateUserRequest;
import com.colendi.financial.user.api.model.response.UserDetailResponse;
import com.colendi.financial.user.api.model.response.UserListResponse;

public interface UserService {

  UserDetailResponse getUserDetail(long userId);

  DoneResponse createUser(CreateUserRequest request);

  UserListResponse getUsers(int page, int size);
}
