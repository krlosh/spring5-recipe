package guru.springframework.recipe.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void setUp() throws Exception {
        this.category = new Category();
    }

    @Test
    public void getId() {
        Long idValue = 4L;
        this.category.setId(idValue);
        assertEquals(idValue,this.category.getId());
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}