package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.conversors.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.conversors.UnitOfMeasureToUnitOfMeasureCommandTest;
import guru.springframework.recipe.domain.UnitOfMeasure;
import guru.springframework.recipe.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository uomRepository;

    private UnitOfMeasureService unitOfMeasureService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.unitOfMeasureService = new UnitOfMeasureServiceImpl(new UnitOfMeasureToUnitOfMeasureCommand(), this
                .uomRepository);
    }

    @Test
    public void listAllUoms() {
        //given
        Iterable<UnitOfMeasure> uomIterator = new ArrayList<>();
        ((ArrayList<UnitOfMeasure>) uomIterator).add(new UnitOfMeasure());
        ((ArrayList<UnitOfMeasure>) uomIterator).add(new UnitOfMeasure());
        when(this.uomRepository.findAll()).thenReturn(uomIterator);

        //when
        Set<UnitOfMeasureCommand> uomSet = this.unitOfMeasureService.listAllUoms() ;

        //then
        assertNotNull(uomSet);
        assertFalse(uomSet.isEmpty());
    }
}