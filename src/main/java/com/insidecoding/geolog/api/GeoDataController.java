package com.insidecoding.geolog.api;

import com.gs.collections.impl.block.factory.Comparators;
import com.insidecoding.geolog.api.dto.CsvConvertor;
import com.insidecoding.geolog.api.dto.SimpleAggr;
import com.insidecoding.geolog.jpa.LogEventJpaRepo;
import com.insidecoding.geolog.jpa.entity.LogFailedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class GeoDataController {
    private static final Logger LOG = LoggerFactory.getLogger(GeoDataController.class);

    @Autowired
    private LogEventJpaRepo repo;

    @Autowired
    private CsvConvertor csvConvertor;

    @CrossOrigin
    @RequestMapping(value = "/api/geodata", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<LogFailedEntity>> getAll() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/aggrbyco/{max}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<SimpleAggr>> aggrbyco(@PathVariable long max) {
        LOG.info("aggregate by country");

        Function<LogFailedEntity, String> function = LogFailedEntity::getCountry;
        List<SimpleAggr> result = getSimpleAggrs(max, function);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @CrossOrigin
    @RequestMapping(value = "/api/aggrbyci/{max}", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<String> aggrbyci(@PathVariable long max) {
        LOG.info("aggregate by city");

        Function<LogFailedEntity, String> function = LogFailedEntity::getCity;
        List<SimpleAggr> result = getSimpleAggrs(max, function);


        return new ResponseEntity<>(csvConvertor.toCsv(result), HttpStatus.OK);
    }

    private List<SimpleAggr> getSimpleAggrs(long max, Function<LogFailedEntity, String> function) {
        List<LogFailedEntity> data = repo.findAll();

        return data.stream()
                .collect(Collectors.groupingBy(function, Collectors.counting())).entrySet().stream()
                .map(entry -> new SimpleAggr(entry.getKey(), entry.getValue()))
                .sorted(Comparators.byLongFunction(SimpleAggr::getValue).reversed())
                .limit(max)
                .collect(Collectors.toList());
    }
}
