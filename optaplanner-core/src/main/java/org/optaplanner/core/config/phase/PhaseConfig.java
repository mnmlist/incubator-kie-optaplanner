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

package com.sankuai.optaplanner.core.config.phase;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.sankuai.optaplanner.core.config.AbstractConfig;
import com.sankuai.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import com.sankuai.optaplanner.core.config.exhaustivesearch.ExhaustiveSearchPhaseConfig;
import com.sankuai.optaplanner.core.config.localsearch.LocalSearchPhaseConfig;
import com.sankuai.optaplanner.core.config.partitionedsearch.PartitionedSearchPhaseConfig;
import com.sankuai.optaplanner.core.config.phase.custom.CustomPhaseConfig;
import com.sankuai.optaplanner.core.config.solver.termination.TerminationConfig;
import com.sankuai.optaplanner.core.config.util.ConfigUtils;

@XmlSeeAlso({
        ConstructionHeuristicPhaseConfig.class,
        CustomPhaseConfig.class,
        ExhaustiveSearchPhaseConfig.class,
        LocalSearchPhaseConfig.class,
        NoChangePhaseConfig.class,
        PartitionedSearchPhaseConfig.class
})
@XmlType(propOrder = {
        "terminationConfig"
})
public abstract class PhaseConfig<Config_ extends PhaseConfig<Config_>> extends AbstractConfig<Config_> {

    // Warning: all fields are null (and not defaulted) because they can be inherited
    // and also because the input config file should match the output config file

    @XmlElement(name = "termination")
    private TerminationConfig terminationConfig = null;

    // ************************************************************************
    // Constructors and simple getters/setters
    // ************************************************************************

    public TerminationConfig getTerminationConfig() {
        return terminationConfig;
    }

    public void setTerminationConfig(TerminationConfig terminationConfig) {
        this.terminationConfig = terminationConfig;
    }

    @Override
    public Config_ inherit(Config_ inheritedConfig) {
        terminationConfig = ConfigUtils.inheritConfig(terminationConfig, inheritedConfig.getTerminationConfig());
        return (Config_) this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
