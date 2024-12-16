package com.itschool.jpa.services;

import com.itschool.jpa.models.Instrument;

import java.util.List;

public interface InstrumentService {
    List<Instrument> getAllInstruments();

    Instrument findInstrumentById(Long id);

    Instrument createInstrument(Instrument instrument);

    void updateInstrumentStock(Long id, Integer quantity);
}
