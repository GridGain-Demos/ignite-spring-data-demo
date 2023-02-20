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

package org.gridgain.demo.springdata;

import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteConfigurer;
import org.gridgain.demo.springdata.model.City;
import org.gridgain.demo.springdata.model.CityKey;
import org.gridgain.demo.springdata.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.gridgain.demo.springdata.config.Constants.CITY_CACHE;
import static org.gridgain.demo.springdata.config.Constants.COUNTRY_CACHE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class IgniteSpringDataDemoApplicationTests {

    @Autowired
    Ignite ignite;

    @Test
    void contextLoads() {
        // just make sure that application is able to start
        assertTrue(ignite.cluster().nodes().size() > 0, "Cluster topology is empty");
    }

    @TestConfiguration
    static class TestConfig {

        /**
         * Override default application config, so application node will start in server mode,
         * thus allowing us to run test without existing cluster
         */
        @Bean
        @Primary
        public IgniteConfigurer testConfigurer() {
            return igniteConfiguration -> {
                igniteConfiguration
                        .setClientMode(false)
                        .setCacheConfiguration(
                                new CacheConfiguration<CityKey, City>(COUNTRY_CACHE),
                                new CacheConfiguration<String, Country>(CITY_CACHE)
                        );
            };
        }

    }

}
