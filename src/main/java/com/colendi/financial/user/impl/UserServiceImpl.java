package com.colendi.financial.user.impl;

import com.colendi.financial.user.UserService;
import com.colendi.financial.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
}
