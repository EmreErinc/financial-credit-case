package com.colendi.financial;

import com.colendi.financial.commons.model.DoneResponse;
import com.colendi.financial.user.api.model.request.CreateUserRequest;
import com.colendi.financial.user.api.model.response.UserListResponse;
import com.colendi.financial.user.domain.entity.UserEntity;
import com.colendi.financial.user.domain.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserApiTests extends BaseApiTest {

  @Autowired
  private UserRepository userRepository;

  @PostConstruct
  void start() {
    userRepository.save(UserEntity.builder()
        .firstName("John")
        .lastName("Doe")
        .build());
  }

  @Test
  void shouldReturnEmptyUserListWhenNoUserExists() throws Exception {
    MvcResult mvcResult = performGet("/v1/users", status().isOk());
    UserListResponse actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserListResponse.class);

    assertFalse(actualResponse.getUsers().isEmpty());
    assertTrue(actualResponse.getUsers().stream().anyMatch(user -> user.getFirstName().equals("John") && user.getLastName().equals("Doe")));
  }

  @Test
  void shouldReturnSuccessWhenUserAdded() throws Exception {
    CreateUserRequest createUserRequest = CreateUserRequest.builder()
        .firstName("Frank")
        .lastName("Sinatra")
        .build();

    MvcResult mvcResult = performPost("/v1/users", createUserRequest, status().isOk());
    DoneResponse actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DoneResponse.class);

    assertTrue(actualResponse.getDone());
  }
}
