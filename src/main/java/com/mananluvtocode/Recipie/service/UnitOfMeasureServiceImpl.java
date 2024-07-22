package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.UnitOfMeasureCommand;
import com.mananluvtocode.Recipie.converters.UnitOfMeasureToUnitOfMeasureCommandConverter;
import com.mananluvtocode.Recipie.repositories.UnitOfMeasureRepository;
import com.mananluvtocode.Recipie.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommandConverter unitOfMeasureToUnitOfMeasureCommandConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, UnitOfMeasureToUnitOfMeasureCommandConverter unitOfMeasureToUnitOfMeasureCommandConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureToUnitOfMeasureCommandConverter = unitOfMeasureToUnitOfMeasureCommandConverter;
    }


    //    @Override
//    public Set<UnitOfMeasureCommand> listAllUom() {
//        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
//                .map(unitOfMeasureToUnitOfMeasureCommandConverter::convert)
//                .collect(Collectors.toSet());
//    }
    @Override
    public Flux<UnitOfMeasureCommand> listAllUom() {
        return unitOfMeasureReactiveRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommandConverter::convert);
    }
}
