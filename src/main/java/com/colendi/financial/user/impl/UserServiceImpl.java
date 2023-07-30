package com.colendi.financial.user.impl;

import com.colendi.financial.commons.DoneResponse;
import com.colendi.financial.user.UserService;
import com.colendi.financial.user.api.model.request.CreateUserRequest;
import com.colendi.financial.user.api.model.response.UserDetail;
import com.colendi.financial.user.domain.UserMapper;
import com.colendi.financial.user.domain.entity.UserEntity;
import com.colendi.financial.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public UserDetail getUserDetail(long userId) {
    UserEntity userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    return userMapper.mapToUserDetail().apply(userEntity);
  }

  @Override
  public DoneResponse createUser(CreateUserRequest request) {
    userRepository.save(UserEntity.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .build());

    return DoneResponse.success();
  }
}
