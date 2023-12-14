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

package com.sankuai.optaplanner.core.impl.heuristic.selector.move.composite;

import java.util.List;
import java.util.stream.Collectors;

import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionCacheType;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionOrder;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.MoveSelectorConfig;
import com.sankuai.optaplanner.core.impl.heuristic.HeuristicConfigPolicy;
import com.sankuai.optaplanner.core.impl.heuristic.selector.move.AbstractMoveSelectorFactory;
import com.sankuai.optaplanner.core.impl.heuristic.selector.move.MoveSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.move.MoveSelectorFactory;

abstract class AbstractCompositeMoveSelectorFactory<Solution_, MoveSelectorConfig_ extends MoveSelectorConfig<MoveSelectorConfig_>>
        extends AbstractMoveSelectorFactory<Solution_, MoveSelectorConfig_> {

    public AbstractCompositeMoveSelectorFactory(MoveSelectorConfig_ moveSelectorConfig) {
        super(moveSelectorConfig);
    }

    protected List<MoveSelector<Solution_>> buildInnerMoveSelectors(List<MoveSelectorConfig> innerMoveSelectorList,
            HeuristicConfigPolicy<Solution_> configPolicy, SelectionCacheType minimumCacheType,
            boolean randomSelection) {
        return innerMoveSelectorList.stream()
                .map(moveSelectorConfig -> {
                    MoveSelectorFactory<Solution_> innerMoveSelectorFactory =
                            MoveSelectorFactory.create(moveSelectorConfig);
                    SelectionOrder selectionOrder = SelectionOrder.fromRandomSelectionBoolean(randomSelection);
                    return innerMoveSelectorFactory.buildMoveSelector(configPolicy, minimumCacheType, selectionOrder);
                }).collect(Collectors.toList());
    }
}
