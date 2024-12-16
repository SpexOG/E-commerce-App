package com.itschool.jpa.controllers;

import com.itschool.jpa.models.Instrument;
import com.itschool.jpa.services.InstrumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {
    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService service) {
        this.instrumentService = service;
    }

    @GetMapping
    public ResponseEntity<List<Instrument>> getAllInstruments() {
        return ResponseEntity.ok(instrumentService.getAllInstruments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrument> getInstrumentById(@PathVariable Long id) {
        return ResponseEntity.ok(instrumentService.findInstrumentById(id));
    }

    @PostMapping
    public ResponseEntity<Instrument> createInstrument(@RequestBody Instrument instrument) {
        Instrument createdInstrument = instrumentService.createInstrument(instrument);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInstrument);
    }

}
