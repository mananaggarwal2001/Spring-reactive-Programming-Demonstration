package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.UnitOfMeasureCommand;
import com.mananluvtocode.Recipie.converters.unitOfMeasureToUnitOfMeasureCommandConverter;
import com.mananluvtocode.Recipie.domain.UnitOfMeasure;
import com.mananluvtocode.Recipie.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final com.mananluvtocode.Recipie.converters.unitOfMeasureToUnitOfMeasureCommandConverter unitOfMeasureToUnitOfMeasureCommandConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommandConverter unitOfMeasureToUnitOfMeasureCommandConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommandConverter = unitOfMeasureToUnitOfMeasureCommandConverter;
    }


    @Override
    public Set<UnitOfMeasureCommand> listAllUom() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommandConverter::convert)
                .collect(Collectors.toSet());
    }
}
