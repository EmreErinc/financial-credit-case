package com.colendi.financial.user.domain;

import com.colendi.financial.user.api.model.response.UserDetail;
import com.colendi.financial.user.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper {

  public Function<UserEntity, UserDetail> mapToUserDetail() {
    return userEntity -> UserDetail.builder()
        .userId(userEntity.getId())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .build();
  }
}
