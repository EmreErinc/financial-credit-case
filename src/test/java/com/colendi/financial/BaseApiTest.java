package com.colendi.financial;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class BaseApiTest {

  @Autowired
  protected MockMvc mockMvc;

  protected final ObjectMapper objectMapper = new ObjectMapper();

  @Container
  private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:12.9-alpine");

  protected MvcResult performGet(String uri, ResultMatcher resultMatcher) throws Exception {
    return this.mockMvc.perform(get(uri))
        .andDo(print())
        .andExpect(resultMatcher)
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  protected MvcResult performPost(String uri, Object request, ResultMatcher resultMatcher) throws Exception {
    return this.mockMvc.perform(post(uri)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(resultMatcher)
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }

  public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.test.database.replace=none",
          "spring.datasource.url=" + database.getJdbcUrl(),
          "spring.datasource.username=" + database.getUsername(),
          "spring.datasource.password=" + database.getPassword()
      );
    }
  }
}
