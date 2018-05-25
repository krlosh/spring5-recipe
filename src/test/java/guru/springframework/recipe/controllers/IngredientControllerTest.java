package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.services.IngredientService;
import guru.springframework.recipe.services.RecipeService;
import guru.springframework.recipe.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class IngredientControllerTest {

    IngredientController controller;

    @Mock
    IngredientService ingredientService;

    @Mock
    RecipeService recipeService;

    @Mock
    UnitOfMeasureService uomService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.controller = new IngredientController(this.recipeService, this.ingredientService, this.uomService);
    }

    @Test
    public void testListIngredients()  throws Exception{
        //given:
        RecipeCommand recipe = new RecipeCommand();
        when(this.recipeService.findRecipeCommandById(anyLong())).thenReturn(recipe);
        //when:
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
        //then
        verify(this.recipeService, times(1)).findRecipeCommandById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        //given:
        IngredientCommand ingredient = new IngredientCommand();
        when(this.ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(ingredient);

        //when:
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
        //then
        verify(this.ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given:
        IngredientCommand ingredient = new IngredientCommand();
        when(this.ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(ingredient);
        when(this.uomService.listAllUoms()).thenReturn(new HashSet<>());
        //when:
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
        //then
        verify(this.ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());

    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //given:
        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(1L);

        when(this.ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(ingredient);
        when(this.uomService.listAllUoms()).thenReturn(new HashSet<>());
        //when:
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
        //then
        verify(this.recipeService, times(1)).findRecipeCommandById(anyLong());

    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        //when
        when(this.ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

        //then
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
        mockMvc.perform(post("/recipe/2/ingredient")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description","Some string")
        ).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
    }
}