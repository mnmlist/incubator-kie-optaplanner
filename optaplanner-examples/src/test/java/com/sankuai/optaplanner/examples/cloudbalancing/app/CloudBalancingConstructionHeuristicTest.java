/*
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sankuai.optaplanner.examples.cloudbalancing.app;

import com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudBalance;
import com.sankuai.optaplanner.examples.common.app.AbstractConstructionHeuristicTest;
import com.sankuai.optaplanner.examples.common.app.CommonApp;

import java.util.stream.Stream;

public class CloudBalancingConstructionHeuristicTest extends AbstractConstructionHeuristicTest<CloudBalance> {

    @Override
    protected CommonApp<CloudBalance> createCommonApp() {
        return new CloudBalancingApp();
    }

    @Override
    protected Stream<String> unsolvedFileNames() {
        return Stream.of("2computers-6processes.xml", "3computers-9processes.xml");
    }
}
