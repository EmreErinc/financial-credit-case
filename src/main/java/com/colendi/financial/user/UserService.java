package com.colendi.financial.user;

import com.colendi.financial.commons.DoneResponse;
import com.colendi.financial.user.api.model.request.CreateUserRequest;
import com.colendi.financial.user.api.model.response.UserDetail;

public interface UserService {

  UserDetail getUserDetail(long userId);

  DoneResponse createUser(CreateUserRequest request);
}
