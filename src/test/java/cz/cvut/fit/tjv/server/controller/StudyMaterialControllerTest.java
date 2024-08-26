package cz.cvut.fit.tjv.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fit.tjv.server.domain.StudyMaterial;
import cz.cvut.fit.tjv.server.service.StudyMaterialService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(StudyMaterialController.class)
class StudyMaterialControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean // without this it won't start
    private StudyMaterialService studyMaterialService;

    final String PATH = "/study-material";

    @Test
    @Order(1)
    void testGetEmptyStudyMaterials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 200
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    @Order(2)
    void testPostStudyMaterials() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        StudyMaterial studyMaterial1 = new StudyMaterial("name1", "authorName1");
        String requestBody = objectMapper.writeValueAsString(studyMaterial1);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    void testGetOneStudyMaterials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().isOk()); // 200
    }

}