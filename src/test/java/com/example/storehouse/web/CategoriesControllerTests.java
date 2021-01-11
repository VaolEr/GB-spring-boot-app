package com.example.storehouse.web;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.service.CategoriesService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoriesController.class)
public class CategoriesControllerTests {

    @MockBean
    private CategoriesService categoriesService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturn200() throws Exception{

        Category returnedCategory = new Category();

        returnedCategory.setId(1000);
        returnedCategory.setName("stationery");
        returnedCategory.setItems(List.of(new Item()));
        //TODO нужно корректнее проверку делать, судя по всему.
        //Не понимаю немного почему неважно какой объект в сравнении (id + name), всё равно корректный тест.
        given(categoriesService.getById(any())).willReturn(returnedCategory);
        mockMvc.perform(get("/api/v1/categories/1000"))
               .andExpect(status().isOk())
               .andExpect(content()
               .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
