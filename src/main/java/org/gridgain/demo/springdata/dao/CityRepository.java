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

package org.gridgain.demo.springdata.dao;

import java.util.List;
import javax.cache.Cache;
import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.Query;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.gridgain.demo.springdata.model.City;
import org.gridgain.demo.springdata.model.CityKey;
import org.springframework.stereotype.Repository;

@RepositoryConfig(cacheName = "City")
@Repository
public interface CityRepository extends IgniteRepository<City, CityKey> {

    public List<Cache.Entry<CityKey, City>> findAllByPopulationGreaterThanEqualOrderByPopulation(int population);

    @Query("SELECT city.name, MAX(city.population), country.name, country.GovernmentForm FROM country " +
        "JOIN city ON city.countrycode = country.code " +
        "GROUP BY city.name, country.name, country.GovernmentForm, city.population " +
        "ORDER BY city.population DESC LIMIT ?")
    public List<List<?>> findMostPopulatedCities(int limit);

    @Query("SELECT * FROM City WHERE id = ?")
    public Cache.Entry<CityKey, City> findById(int id);
}
