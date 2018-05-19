package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.CategoryCommand;
import guru.springframework.recipe.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long ID = new Long(1L);

    CategoryToCategoryCommand conversor;

    @Before
    public void setUp() throws Exception {
        conversor = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullParameter(){
        assertNull(conversor.convert(null));
    }

    @Test
    public void testEmpyObject() {
        assertNotNull(conversor.convert(new Category()));
    }

    @Test
    public void convert(){
        //given
        Category category = new Category();
        category.setDescription(DESCRIPTION);
        category.setId(ID);

        //wben
        CategoryCommand result = this.conversor.convert(category);

        //then
        assertNotNull(result);
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(ID, result.getId());
    }
}