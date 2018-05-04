package guru.springframework.recipe.services;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.recipeService = new RecipeServiceImpl(this.recipeRepository);
    }


    @Test
    public void getRecipes() {
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(new Recipe());

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> result = this.recipeService.getRecipes();

        assertEquals(1, result.size());
        verify(recipeRepository, times(1)).findAll();

    }
}