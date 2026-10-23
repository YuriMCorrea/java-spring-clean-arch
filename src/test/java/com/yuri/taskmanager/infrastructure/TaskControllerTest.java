package com.yuri.taskmanager.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.jsonpath.JsonPath.read;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class TaskControllerTest {
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    void should_save_and_retrieve_task_by_is() throws Exception {
        // Test Variables
        String name = "Testing Spring Rest Docs";
        String description = "Read official documentation";
        Map<String, String> taskRequest = new HashMap<>();

        // GIVEN
        taskRequest.put("name", name);
        taskRequest.put("description", description);

        // WHEN
        String responseJson;
        responseJson = this.mockMvc.perform(
                post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest))
            )
        // THEN
                .andExpect(status().isCreated())
                .andDo(document("create-task",
                        requestFields(
                                fieldWithPath("name"). description("Name of the task"),
                                fieldWithPath("description"). description("Task detailed description")
                        ),
                        responseFields(
                                fieldWithPath("id"). description("Unique identifier of the task"),
                                fieldWithPath("name"). description("Name of the task"),
                                fieldWithPath("description"). description("Task detailed description"),
                                fieldWithPath("status"). description("Current status of the Task")
                        )
                ))
                .andReturn()
                .getResponse()
                .getContentAsString();
        // GIVEN
        var generatedId = read(responseJson, "$.id");

        // WHEN
        this.mockMvc.perform(
                get("/api/v1/tasks/{id}", generatedId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(generatedId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.description").value(description))
                .andDo(document("get-task-by-id",
                        pathParameters(
                                parameterWithName("id"). description("Unique identifier of the task")
                        ),
                        responseFields(
                                fieldWithPath("id"). description("Unique identifier of the task"),
                                fieldWithPath("name"). description("Name of the task"),
                                fieldWithPath("description"). description("Task detailed description"),
                                fieldWithPath("status"). description("Current status of the Task")

                        )
                ));

    }

}