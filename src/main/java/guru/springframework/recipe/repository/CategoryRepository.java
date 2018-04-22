package guru.springframework.recipe.repository;

import guru.springframework.recipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository  extends CrudRepository<Category,Long> {
}
