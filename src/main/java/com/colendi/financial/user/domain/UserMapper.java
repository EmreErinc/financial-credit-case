package com.colendi.financial.user.domain;

import com.colendi.financial.user.api.model.response.UserDetailResponse;
import com.colendi.financial.user.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper {

  public Function<UserEntity, UserDetailResponse> mapToUserDetail() {
    return userEntity -> UserDetailResponse.builder()
        .userId(userEntity.getId())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .build();
  }
}
