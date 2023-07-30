package com.colendi.financial.user.api;

import com.colendi.financial.commons.DoneResponse;
import com.colendi.financial.user.UserService;
import com.colendi.financial.user.api.model.request.CreateUserRequest;
import com.colendi.financial.user.api.model.response.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserDetail> getMe() {
    return ResponseEntity.of(Optional.of(UserDetail.builder().firstName("emre").lastName("erinç").build()));
  }

  @PostMapping
  public ResponseEntity<DoneResponse> createUser(@RequestBody CreateUserRequest request) {
    return ResponseEntity.ok(userService.createUser(request));
  }
}
