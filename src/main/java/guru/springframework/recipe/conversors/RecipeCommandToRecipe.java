package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private NotesCommandToNotes notesConverter;
    private CategoryCommandToCategory categoryConverter;
    private IngredientCommandToIngredient ingredientConversor;

    public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, CategoryCommandToCategory categoryConverter, IngredientCommandToIngredient ingredientConversor) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConversor = ingredientConversor;
    }

    @Override
    @Nullable
    @Synchronized
    public Recipe convert(RecipeCommand source) {
        if(source == null) {
            return null;
        }
        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        if(source.getNotes()!=null) {
            recipe.setNotes(this.notesConverter.convert(source.getNotes()));
        }
        if(source.getCategories()!=null && !source.getCategories().isEmpty()){
            source.getCategories().forEach(categoryCommand -> recipe.getCategories().add(categoryConverter.convert(categoryCommand)));
        }
        if(source.getIngredients()!=null && !source.getIngredients().isEmpty()){
            source.getIngredients().forEach(ingredientCommand -> recipe.addIngredient(this.ingredientConversor.convert(ingredientCommand)));
        }
        return recipe;
    }
}
