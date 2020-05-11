/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.gridgain.demo.springdata.controller;

import java.util.List;
import javax.cache.Cache;
import org.gridgain.demo.springdata.model.Country;
import org.gridgain.demo.springdata.model.dto.CityDTO;
import org.gridgain.demo.springdata.model.dto.CountryDTO;
import org.gridgain.demo.springdata.service.WorldDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorldDatabaseController {
    @Autowired WorldDatabaseService service;

    @GetMapping("/api/countries")
    public List<CountryDTO> getCountriesByPopulation(@RequestParam (value = "population", required = true) int population) {
        return service.getCountries(population);
    }

    @GetMapping("/api/countries/mostPopulated")
    public List<CountryDTO> getMostPopulatedCountries(@RequestParam (value = "limit", required = false) Integer limit) {
        return service.getMostPopulatedCountries(limit);
    }

    @GetMapping("/api/cities")
    public List<CityDTO> getCitiesByPopulation(@RequestParam (value = "population", required = true) int population) {
        return service.getCities(population);
    }

    @GetMapping("/api/cities/mostPopulated")
    public List<List<?>> getMostPopulatedCities(@RequestParam (value = "limit", required = false) Integer limit) {
        return service.getMostPopulatedCities(limit);
    }

    @PutMapping("/api/cities/{id}")
    public CityDTO updateCityPopulation(@PathVariable Integer id, @RequestBody CityDTO cityDTO) {
        return service.updateCityPopulation(id, cityDTO.getPopulation());
    }

}
