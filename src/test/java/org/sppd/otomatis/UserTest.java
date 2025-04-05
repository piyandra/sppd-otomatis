package org.sppd.otomatis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sppd.otomatis.dto.UserRequest;
import org.sppd.otomatis.dto.WebResponse;
import org.sppd.otomatis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Register User Success")
    void registerSuccessTest() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Viandra Stefani");
        userRequest.setPassword("Hello Dunia");
        userRequest.setUsername("testing123");

        mockMvc.perform(
                post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest))

        ).andExpectAll(status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.message").value("User Registered Successfully")
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            Assertions.assertEquals("User Registered Successfully", response.getMessage());
        });
    }
    @Test
    @DisplayName("Register Failed Username and Password Test")
    void registerfailedPasswordAndUsernameTest() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("ViandraStefani");
        userRequest.setPassword("hell");
        userRequest.setUsername("ja");

        mockMvc.perform(post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)
        )).andExpectAll(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Data Tidak valid"))
                .andExpect(jsonPath("$.data.password").value("Password must be at least 8 characters"))
                .andExpect(jsonPath("$.data.username").value("Username Must Be 3 to 100 characters"));
    }

    @Test
    @DisplayName("Register Success And Get Token Test")
    public void registerSuccessGetTokenTest() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("viandrastef");
        userRequest.setPassword("HelloDunia");
        userRequest.setName("Viandra Stefani");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andReturn();
        UserRequest request = new UserRequest();
        request.setUsername(userRequest.getUsername());
        request.setPassword(userRequest.getPassword());

        MvcResult loginResponse = mockMvc.perform(get("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        String responses = loginResponse.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responses);
        Assertions.assertNotNull(node.get("data").get("token").asText());
    }
    @Test
    @DisplayName("Log Out Success Test")
    public void logOutSuccessTest() throws Exception{
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("viandrastef");
        userRequest.setPassword("HelloDunia");
        userRequest.setName("Viandra Stefani");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andReturn();
        UserRequest request = new UserRequest();
        request.setUsername(userRequest.getUsername());
        request.setPassword(userRequest.getPassword());

        MvcResult loginResponse = mockMvc.perform(get("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        String responses = loginResponse.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responses);
        String token = node.get("data").get("token").asText();
        mockMvc.perform(get("/api/v1/user/logout")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authoriztion", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(token)))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("Sukses")
                ).andDo(result -> {
                    WebResponse<String> response1 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    Assertions.assertEquals("Sukses", response1.getMessage());
                });
    }
    @Test
    @DisplayName("Get Me Error Token Invalid Test")
    public void getMeErrorTest() throws Exception{
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("viandrastef");
        userRequest.setPassword("HelloDunia");
        userRequest.setName("Viandra Stefani");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andReturn();
        UserRequest request = new UserRequest();
        request.setUsername(userRequest.getUsername());
        request.setPassword(userRequest.getPassword());

        MvcResult loginResponse = mockMvc.perform(get("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        String responses = loginResponse.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responses);
        String token = node.get("data").get("token").asText();
        mockMvc.perform(get("/api/v1/user/logout")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authoriztion", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(token)))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("Sukses")
                ).andDo(result -> {
                    WebResponse<String> response1 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    Assertions.assertEquals("Sukses", response1.getMessage());
                }).andReturn();
        mockMvc.perform(get("/api/v1/users/" + userRequest.getUsername() + "/me")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token + "123")
        .content(objectMapper.writeValueAsString(token)))
                .andExpectAll(status().isUnauthorized(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("Token Invalid")
                ).andDo(result1 -> {
                    WebResponse<String> response1 = objectMapper.readValue(result1.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    Assertions.assertEquals("Token Invalid", response1.getMessage());
                });

    }
    @Test
    @DisplayName("Get Me Success Test")
    public void getMeSuccessTest() throws Exception{
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("viandrastef");
        userRequest.setPassword("HelloDunia");
        userRequest.setName("Viandra Stefani");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andReturn();
        UserRequest request = new UserRequest();
        request.setUsername(userRequest.getUsername());
        request.setPassword(userRequest.getPassword());

        MvcResult loginResponse = mockMvc.perform(get("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        String responses = loginResponse.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responses);
        String token = node.get("data").get("token").asText();
        MvcResult authorization = mockMvc.perform(get("/api/v1/users/" + userRequest.getUsername() + "/me")
                        .header("Authorization", "Bearer " + token))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String respons = authorization.getResponse().getContentAsString();
        ObjectMapper objectMapper1 = new ObjectMapper();
        JsonNode node1 = objectMapper1.readTree(respons);
        Assertions.assertEquals(userRequest.getUsername(), node1.get("data").get("username").asText());

    }
    @Test
    @DisplayName("Log Out Error Test")
    public void logOutErrorTest() throws Exception{
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("viandrastef");
        userRequest.setPassword("HelloDunia");
        userRequest.setName("Viandra Stefani");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andReturn();
        UserRequest request = new UserRequest();
        request.setUsername(userRequest.getUsername());
        request.setPassword(userRequest.getPassword());

        MvcResult loginResponse = mockMvc.perform(get("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        String responses = loginResponse.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responses);
        node.get("data").get("token").asText();
        mockMvc.perform(get("/api/v1/user/logout")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(UUID.randomUUID().toString())))
                .andExpectAll(status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value("User Not Found")
                ).andDo(result -> {
                    WebResponse<String> response1 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    Assertions.assertEquals("User Not Found", response1.getMessage());
                });
    }
    @Test
    @DisplayName("Register Failed Username Test")
    void registerfailedUsername() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("ViandraStefani");
        userRequest.setPassword("helloyeahh");
        userRequest.setUsername("ja");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)
                        )).andExpectAll(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Data Tidak valid"))
                .andExpect(jsonPath("$.data.username").value("Username Must Be 3 to 100 characters"));
    }
    @Test
    void registerfailedPassword() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("ViandraStefani");
        userRequest.setPassword("hell");
        userRequest.setUsername("ja");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)
                        )).andExpectAll(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Data Tidak valid"))
                .andExpect(jsonPath("$.data.password").value("Password must be at least 8 characters"));
    }
    @Test
    @DisplayName("Log In Error Test")
    public void loginErrorTest() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("viandrastef");
        userRequest.setPassword("HelloDunia");
        userRequest.setName("Viandra Stefani");

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andReturn();
        UserRequest request = new UserRequest();
        request.setUsername(userRequest.getUsername() + "123");
        request.setPassword(userRequest.getPassword());

        MvcResult loginResponse = mockMvc.perform(get("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User atau password tidak valid"))
                .andReturn();
        String responses = loginResponse.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responses);
        Assertions.assertNull(node.get("body"));
        Assertions.assertNotNull(node.get("message"));
        Assertions.assertEquals("User atau password tidak valid", node.get("message").asText());
    }

}


