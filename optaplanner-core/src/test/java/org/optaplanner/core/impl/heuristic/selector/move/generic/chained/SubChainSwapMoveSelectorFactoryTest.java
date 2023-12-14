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

package com.sankuai.optaplanner.core.impl.heuristic.selector.move.generic.chained;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionCacheType;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.chained.SubChainSwapMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.value.ValueSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.value.chained.SubChainSelectorConfig;
import com.sankuai.optaplanner.core.impl.heuristic.HeuristicConfigPolicy;
import com.sankuai.optaplanner.core.impl.testdata.domain.chained.TestdataChainedSolution;

class SubChainSwapMoveSelectorFactoryTest {

    @Test
    void buildBaseMoveSelector() {
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig("chainedObject");
        SubChainSelectorConfig leftSubChainSelectorConfig = new SubChainSelectorConfig();
        leftSubChainSelectorConfig.setValueSelectorConfig(valueSelectorConfig);
        SubChainSelectorConfig rightSubChainSelectorConfig = new SubChainSelectorConfig();
        rightSubChainSelectorConfig.setValueSelectorConfig(valueSelectorConfig);
        SubChainSwapMoveSelectorConfig config = new SubChainSwapMoveSelectorConfig();
        config.setSubChainSelectorConfig(leftSubChainSelectorConfig);
        config.setSecondarySubChainSelectorConfig(rightSubChainSelectorConfig);
        SubChainSwapMoveSelectorFactory factory = new SubChainSwapMoveSelectorFactory(config);

        HeuristicConfigPolicy heuristicConfigPolicy = mock(HeuristicConfigPolicy.class);
        when(heuristicConfigPolicy.getSolutionDescriptor()).thenReturn(TestdataChainedSolution.buildSolutionDescriptor());

        SubChainSwapMoveSelector selector = (SubChainSwapMoveSelector) factory.buildBaseMoveSelector(heuristicConfigPolicy,
                SelectionCacheType.JUST_IN_TIME, true);
        assertThat(selector.leftSubChainSelector).isNotNull();
        assertThat(selector.rightSubChainSelector).isNotNull();
        assertThat(selector.variableDescriptor).isNotNull();
        assertThat(selector.randomSelection).isTrue();
    }
}
