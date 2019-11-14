package com.trilogyed.noteservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.noteservice.dao.NoteDao;
import com.trilogyed.noteservice.model.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteDao noteDao;

    private JacksonTester<Note> jacksonTester;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        mapper = new ObjectMapper();
    }

    @Test
    public void saveNote() throws Exception {
        Note noteInput = new Note( 1, "note");
        String noteInputJSON = mapper.writeValueAsString(noteInput);
        Note noteOutput = new Note( 1, 1, "note");
        String noteOutputJSON = mapper.writeValueAsString(noteOutput);

        given(noteDao.addNote(noteInput))
                .willReturn(noteOutput);

        mockMvc.perform(
                post("/notes")
                        .content(noteInputJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(noteOutputJSON));
    }

    @Test
    public void getNote() throws Exception {
        Note noteOutput = new Note( 1, 1, "note");
        String noteOutputJSON = mapper.writeValueAsString(noteOutput);

        given(noteDao.getNoteById(1))
                .willReturn(noteOutput);

        mockMvc.perform(
                get("/notes/{id}", 1)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(noteOutputJSON));
    }

    @Test
    public void getAllNotes() throws Exception {
        Note n1 = new Note( 1, 1, "note");
        Note n2 = new Note( 2, 1, "note");

        List<Note> noteList = new ArrayList<>(Arrays.asList(n1, n2));
        String noteListJSON = mapper.writeValueAsString(noteList);

        given(noteDao.getAllNotes())
                .willReturn(noteList);

        mockMvc.perform(
                get("/notes")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(noteListJSON));
    }

    @Test
    public void updateNote() throws Exception {
        Note after = new Note( 1, 1, "note");
        String afterJSON = mapper.writeValueAsString(after);

        mockMvc.perform(
                put("/notes")
                        .content(afterJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNote() throws Exception {
        mockMvc.perform(
                delete("/notes/{id}", 1)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}