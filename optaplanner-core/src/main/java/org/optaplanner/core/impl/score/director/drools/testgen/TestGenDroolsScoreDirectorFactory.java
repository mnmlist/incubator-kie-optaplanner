/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
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
package com.sankuai.optaplanner.core.impl.score.director.drools.testgen;

import java.io.File;
import java.util.List;

import org.kie.api.KieBase;
import com.sankuai.optaplanner.core.api.score.Score;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.score.director.drools.DroolsScoreDirectorFactory;

public class TestGenDroolsScoreDirectorFactory<Solution_, Score_ extends Score<Score_>>
        extends DroolsScoreDirectorFactory<Solution_, Score_> {

    private final List<String> scoreDrlList;
    private final List<File> scoreDrlFileList;

    /**
     * @param solutionDescriptor never null
     * @param kieBase never null
     * @param scoreDrlList
     * @param scoreDrlFileList
     */
    public TestGenDroolsScoreDirectorFactory(SolutionDescriptor<Solution_> solutionDescriptor,
            KieBase kieBase, List<String> scoreDrlList, List<File> scoreDrlFileList) {
        super(solutionDescriptor, kieBase);
        this.scoreDrlList = scoreDrlList;
        this.scoreDrlFileList = scoreDrlFileList;
    }

    @Override
    public TestGenDroolsScoreDirector<Solution_, Score_> buildScoreDirector(
            boolean lookUpEnabled, boolean constraintMatchEnabledPreference) {
        return new TestGenDroolsScoreDirector<>(this, lookUpEnabled, constraintMatchEnabledPreference, scoreDrlList,
                scoreDrlFileList);
    }

}
