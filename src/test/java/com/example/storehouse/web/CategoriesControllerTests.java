package com.example.storehouse.web;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.storehouse.model.Category;
import com.example.storehouse.model.Item;
import com.example.storehouse.service.CategoriesService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoriesController.class)
public class CategoriesControllerTests {

    static final int TEST_CATEGORY_ID_200 = 1000;
    static final String TEST_CATEGORY_NAME_200 = "stationery";

    static final int TEST_CATEGORY_ID_404 = 9999;
    static final String TEST_CATEGORY_NAME_404 = "sdfrkldfhjg";

    @Value(value = "${app.endpoints.base_path}" + "${app.endpoints.categories.base_url}/")
    String testCategoryPath;

    @MockBean
    private CategoriesService categoriesService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturn200() throws Exception {

        Category returnedCategory = new Category();

        returnedCategory.setId(TEST_CATEGORY_ID_200);
        returnedCategory.setName(TEST_CATEGORY_NAME_200);
        returnedCategory.setItems(List.of(new Item()));

        given(categoriesService.getById(TEST_CATEGORY_ID_200)).willReturn(returnedCategory);

        mockMvc.perform(get(testCategoryPath + TEST_CATEGORY_ID_200))
               .andExpect(status().isOk())
               .andExpect(content()
                              .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.data.id").value(equalTo(TEST_CATEGORY_ID_200)))
               .andExpect(jsonPath("$.data.name").value(equalTo(TEST_CATEGORY_NAME_200)))
               .andDo(print());
    }

//    @Test
//    public void testReturn404() throws Exception {
//
//        Category returnedCategory = new Category();
////        returnedCategory.setId(TEST_CATEGORY_ID_404);
////        returnedCategory.setName(TEST_CATEGORY_NAME_404);
////        returnedCategory.setItems(List.of(new Item()));
//
//        //TODO как понимаю .willReturn эмулирует то, что мы должны получить.
//        //Получается в данной подстановке мы должны получить json
//        // {"response_status":"404 NOT_FOUND","error_message":"Not found entity with type is 'Category' and id is '9999'","data":[]}
//        // Я попытался прокинуть его как это далется через categoriesRepository, но чтот не взлетело.
//        // Потому что я не понимаю как формируется ответ в формате {"response_status":"404 NOT_FOUND","error_message":"Not found entity with type is 'Category' and id is '9999'","data":[]}
//        // Я вижу что это идёт от ValidationUtil, а что с этим делать пока не понял.
//        given(categoriesService.getById(TEST_CATEGORY_ID_404)).willReturn(returnedCategory);
//
//        mockMvc.perform(get(testCategoryPath + TEST_CATEGORY_ID_404))
//               .andExpect(status().isNotFound())
//               .andExpect(content()
//                              .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//               //.andExpect(jsonPath("$.error_message").value(equalTo("Not found entity with type is 'Category' and id is '9999'")))
//               //.andExpect(jsonPath("$.data.name").value(equalTo(TEST_CATEGORY_NAME)))
//               .andDo(print());
//    }

}
