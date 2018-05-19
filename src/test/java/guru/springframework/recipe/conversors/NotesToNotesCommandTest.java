package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.NotesCommand;
import guru.springframework.recipe.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    public static final String RECIPE_NOTES = "notes";
    public static final Long ID = new Long(1L);

    NotesToNotesCommand conversor;

    @Before
    public void setUp() throws Exception {
        conversor = new NotesToNotesCommand();
    }

    @Test
    public void testNullParameter(){
        assertNull(conversor.convert(null));
    }

    @Test
    public void testEmpyObject() {
        assertNotNull(conversor.convert(new Notes()));
    }

    @Test
    public void convert(){
        //given
        Notes uom = new Notes();
        uom.setRecipeNotes(RECIPE_NOTES);
        uom.setId(ID);

        //wben
        NotesCommand result = this.conversor.convert(uom);

        //then
        assertNotNull(result);
        assertEquals(RECIPE_NOTES, result.getRecipeNotes());
        assertEquals(ID, result.getId());
    }
}