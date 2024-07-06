package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.UnitOfMeasureCommand;
import com.mananluvtocode.Recipie.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUom();
}
