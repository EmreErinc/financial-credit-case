package com.colendi.financial.user.api;

import com.colendi.financial.commons.model.DoneResponse;
import com.colendi.financial.user.UserService;
import com.colendi.financial.user.api.model.request.CreateUserRequest;
import com.colendi.financial.user.api.model.response.UserListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<DoneResponse> createUser(@RequestBody CreateUserRequest request) {
    return ResponseEntity.ok(userService.createUser(request));
  }

  @GetMapping
  public ResponseEntity<UserListResponse> getUsers(@RequestParam(required = false, defaultValue = "1") int page,
                                                   @RequestParam(required = false, defaultValue = "10") int size) {
    return ResponseEntity.ok(userService.getUsers(page, size));
  }
}
