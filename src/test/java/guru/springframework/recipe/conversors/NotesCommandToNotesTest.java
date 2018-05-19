package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.NotesCommand;
import guru.springframework.recipe.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    public static final String RECIPE_NOTES = "notes";
    public static final Long ID = new Long(1L);

    NotesCommandToNotes conversor;

    @Before
    public void setUp() throws Exception {
        conversor = new NotesCommandToNotes();
    }

    @Test
    public void testNullParameter(){
        assertNull(conversor.convert(null));
    }

    @Test
    public void testEmpyObject() {
        assertNotNull(conversor.convert(new NotesCommand()));
    }

    @Test
    public void convert(){
        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setRecipeNotes(RECIPE_NOTES);
        notesCommand.setId(ID);

        //wben
        Notes result = this.conversor.convert(notesCommand);

        //then
        assertNotNull(result);
        assertEquals(RECIPE_NOTES, result.getRecipeNotes());
        assertEquals(ID, result.getId());
    }
}