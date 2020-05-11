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

package org.gridgain.demo.springdata.service;

import java.util.ArrayList;
import java.util.List;
import javax.cache.Cache;
import org.gridgain.demo.springdata.dao.CityRepository;
import org.gridgain.demo.springdata.dao.CountryRepository;
import org.gridgain.demo.springdata.model.City;
import org.gridgain.demo.springdata.model.CityKey;
import org.gridgain.demo.springdata.model.Country;
import org.gridgain.demo.springdata.model.dto.CityDTO;
import org.gridgain.demo.springdata.model.dto.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorldDatabaseService {
    @Autowired CountryRepository countryDao;

    @Autowired CityRepository cityDao;

    public List<CountryDTO> getCountries(int population) {
        List<Cache.Entry<String, Country>> entries =
            countryDao.findByPopulationGreaterThanEqualOrderByPopulationDesc(population);

        return convertCountryToDTO(entries);
    }

    public List<CityDTO> getCities(int population) {
        List<Cache.Entry<CityKey,City>> entries = cityDao.findAllByPopulationGreaterThanEqualOrderByPopulation(population);

        return convertCityToDTO(entries);
    }

    public List<CountryDTO> getMostPopulatedCountries(Integer limit) {
        List<List<?>> entries = countryDao.findMostPopulatedCountries(limit == null ? 5 : limit);

        List<CountryDTO> result = new ArrayList<>(entries.size());

        for (List<?> row: entries) {
            CountryDTO country = new CountryDTO();

            country.setName((String)row.get(0));
            country.setPopulation((Integer)row.get(1));

            result.add(country);
        }

        return result;
    }

    public List<List<?>> getMostPopulatedCities(Integer limit) {
        List<List<?>> entries = cityDao.findMostPopulatedCities(limit == null ? 5 : limit);

        return entries;
    }

    public CityDTO updateCityPopulation(int cityId, int population) {
        Cache.Entry<CityKey, City> entry = cityDao.findById(cityId);

        entry.getValue().setPopulation(population);

        cityDao.save(entry.getKey(), entry.getValue());

        return new CityDTO(entry.getKey(), entry.getValue());
    }


    private List<CityDTO> convertCityToDTO(List<Cache.Entry<CityKey,City>> entries) {
        List<CityDTO> cities = new ArrayList<>(entries.size());

        for (Cache.Entry<CityKey, City> entry: entries)
            cities.add(new CityDTO(entry.getKey(), entry.getValue()));

        return cities;
    }

    private List<CountryDTO> convertCountryToDTO(List<Cache.Entry<String, Country>> entries) {
        List<CountryDTO> countries = new ArrayList<>(entries.size());

        for (Cache.Entry<String, Country> entry: entries)
            countries.add(new CountryDTO(entry.getKey(), entry.getValue()));

        return countries;
    }
}
