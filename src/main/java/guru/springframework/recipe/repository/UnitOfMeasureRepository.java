package guru.springframework.recipe.repository;

import guru.springframework.recipe.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasureRepository  extends CrudRepository<UnitOfMeasure,Long> {
}
