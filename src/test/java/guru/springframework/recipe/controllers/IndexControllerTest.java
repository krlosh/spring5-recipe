package guru.springframework.recipe.controllers;


import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.indexController = new IndexController(this.recipeService);
    }

    @Test
    public void getIndexPage() {

        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(new Recipe());
        Recipe secondRecipe = new Recipe();
        secondRecipe.setId(1L);
        recipeData.add(secondRecipe);
        when(this.recipeService.getRecipes()).thenReturn(recipeData);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        String indexPage = this.indexController.getIndexPage(model);

        assertEquals("index",indexPage);

        verify(this.recipeService, times(1)).getRecipes();
        verify(model,times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        assertEquals(2,argumentCaptor.getValue().size());
    }
}