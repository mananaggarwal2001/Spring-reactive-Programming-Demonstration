package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.UnitOfMeasureCommand;
import com.mananluvtocode.Recipie.domain.UnitOfMeasure;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UnitOfMeasureService {
//    Set<UnitOfMeasureCommand> listAllUom();
    Flux<UnitOfMeasureCommand> listAllUom();
}
