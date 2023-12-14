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

package com.sankuai.optaplanner.core.impl.heuristic.selector.move.generic;

import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionCacheType;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionOrder;
import com.sankuai.optaplanner.core.config.heuristic.selector.entity.pillar.PillarSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.PillarChangeMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.value.ValueSelectorConfig;
import com.sankuai.optaplanner.core.impl.heuristic.HeuristicConfigPolicy;
import com.sankuai.optaplanner.core.impl.heuristic.selector.entity.pillar.PillarSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.entity.pillar.PillarSelectorFactory;
import com.sankuai.optaplanner.core.impl.heuristic.selector.move.AbstractMoveSelectorFactory;
import com.sankuai.optaplanner.core.impl.heuristic.selector.move.MoveSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.ValueSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.ValueSelectorFactory;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class PillarChangeMoveSelectorFactory<Solution_>
        extends AbstractMoveSelectorFactory<Solution_, PillarChangeMoveSelectorConfig> {

    public PillarChangeMoveSelectorFactory(PillarChangeMoveSelectorConfig moveSelectorConfig) {
        super(moveSelectorConfig);
    }

    @Override
    protected MoveSelector<Solution_> buildBaseMoveSelector(HeuristicConfigPolicy<Solution_> configPolicy,
            SelectionCacheType minimumCacheType, boolean randomSelection) {
        PillarSelectorConfig pillarSelectorConfig_ =
                defaultIfNull(config.getPillarSelectorConfig(), new PillarSelectorConfig());
        List<String> variableNameIncludeList = config.getValueSelectorConfig() == null
                || config.getValueSelectorConfig().getVariableName() == null ? null
                        : Collections.singletonList(config.getValueSelectorConfig().getVariableName());
        PillarSelector<Solution_> pillarSelector = PillarSelectorFactory.<Solution_> create(pillarSelectorConfig_)
                .buildPillarSelector(configPolicy, config.getSubPillarType(), config.getSubPillarSequenceComparatorClass(),
                        minimumCacheType, SelectionOrder.fromRandomSelectionBoolean(randomSelection), variableNameIncludeList);
        ValueSelectorConfig valueSelectorConfig_ = defaultIfNull(config.getValueSelectorConfig(),
                new ValueSelectorConfig());
        SelectionOrder selectionOrder = SelectionOrder.fromRandomSelectionBoolean(randomSelection);
        ValueSelector<Solution_> valueSelector = ValueSelectorFactory.<Solution_> create(valueSelectorConfig_)
                .buildValueSelector(configPolicy, pillarSelector.getEntityDescriptor(), minimumCacheType, selectionOrder);
        return new PillarChangeMoveSelector<>(pillarSelector, valueSelector, randomSelection);
    }
}
