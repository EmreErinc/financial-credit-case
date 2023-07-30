package com.colendi.financial.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoneResponse {

  Boolean done;

  public static DoneResponse success() {
    return DoneResponse.builder().done(true).build();
  }

  public static DoneResponse fail() {
    return DoneResponse.builder().done(false).build();
  }

}
