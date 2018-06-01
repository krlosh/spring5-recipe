package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.ImageService;
import guru.springframework.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @Mock
    private RecipeService recipeService;

    private ImageController imageController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(this.imageController)
                .setControllerAdvice(ControllerExceptionHandler.class).build();
    }

    @Test
    public void testFormGet() throws Exception {
        //given
        RecipeCommand recipe = new RecipeCommand();
        recipe.setId(1L);

        when(this.recipeService.findRecipeCommandById(anyLong())).thenReturn(recipe);

        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        //verify
        verify(this.recipeService, times(1)).findRecipeCommandById(anyLong());
    }

    @Test
    public void testHandleImagePost() throws Exception{
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt","text/plain","Spring framework guru".getBytes());

        //when
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","/recipe/1/show"));

        //verify
        verify(this.imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void testRenderImageFromDB() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        String s ="fake text";
        Byte[] bytes = new Byte[s.length()];
        int i=0;
        for(byte b:s.getBytes()){
            bytes[i++] = b;
        }
        recipeCommand.setImage(bytes);

        when(this.recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage")).andExpect(status().isOk())
                .andReturn()
                .getResponse();

        byte[] imageBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, imageBytes.length);
    }

    @Test
    public void testGetImageNumberFormatException() throws Exception {
        this.mockMvc.perform(get("/recipe/asd/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}
