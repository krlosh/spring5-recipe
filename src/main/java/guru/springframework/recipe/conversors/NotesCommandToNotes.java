package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.NotesCommand;
import guru.springframework.recipe.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Nullable
    @Synchronized
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if(notesCommand == null ) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(notesCommand.getId());
        notes.setRecipeNotes(notesCommand.getRecipeNotes());
        return notes;
    }

}
