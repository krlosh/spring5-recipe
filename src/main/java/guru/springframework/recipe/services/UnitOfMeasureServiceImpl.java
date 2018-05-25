package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.conversors.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureToUnitOfMeasureCommand converter;
    private UnitOfMeasureRepository uomRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand, UnitOfMeasureRepository uomRepository) {
        this.converter = unitOfMeasureToUnitOfMeasureCommand;
        this.uomRepository = uomRepository;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(this.uomRepository.findAll().spliterator(), false).map(converter::convert).collect
                (Collectors.toSet());

    }
}
