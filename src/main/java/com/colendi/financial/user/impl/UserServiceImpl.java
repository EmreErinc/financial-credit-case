package com.colendi.financial.user.impl;

import com.colendi.financial.commons.DoneResponse;
import com.colendi.financial.user.UserService;
import com.colendi.financial.user.api.model.request.CreateUserRequest;
import com.colendi.financial.user.api.model.response.UserDetailResponse;
import com.colendi.financial.user.api.model.response.UserListResponse;
import com.colendi.financial.user.domain.UserMapper;
import com.colendi.financial.user.domain.entity.UserEntity;
import com.colendi.financial.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public UserDetailResponse getUserDetail(long userId) {
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

  @Override
  public UserListResponse getUsers(int page, int size) {
    List<UserDetailResponse> users = userRepository.findAll(PageRequest.of(page - 1, size))
        .map(userMapper.mapToUserDetail())
        .toList();

    return UserListResponse.builder()
        .users(users)
        .build();
  }
}
