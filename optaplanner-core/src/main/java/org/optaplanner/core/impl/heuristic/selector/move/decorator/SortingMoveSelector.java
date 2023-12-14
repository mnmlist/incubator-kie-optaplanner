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

package com.sankuai.optaplanner.core.impl.heuristic.selector.move.decorator;

import java.util.Iterator;

import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionCacheType;
import com.sankuai.optaplanner.core.impl.heuristic.move.Move;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorter;
import com.sankuai.optaplanner.core.impl.heuristic.selector.move.MoveSelector;
import com.sankuai.optaplanner.core.impl.solver.scope.SolverScope;

public class SortingMoveSelector<Solution_> extends AbstractCachingMoveSelector<Solution_> {

    protected final SelectionSorter<Solution_, Move<Solution_>> sorter;

    public SortingMoveSelector(MoveSelector<Solution_> childMoveSelector, SelectionCacheType cacheType,
            SelectionSorter<Solution_, Move<Solution_>> sorter) {
        super(childMoveSelector, cacheType);
        this.sorter = sorter;
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public void constructCache(SolverScope<Solution_> solverScope) {
        super.constructCache(solverScope);
        sorter.sort(solverScope.getScoreDirector(), cachedMoveList);
        logger.trace("    Sorted cachedMoveList: size ({}), moveSelector ({}).",
                cachedMoveList.size(), this);
    }

    @Override
    public boolean isNeverEnding() {
        return false;
    }

    @Override
    public Iterator<Move<Solution_>> iterator() {
        return cachedMoveList.iterator();
    }

    @Override
    public String toString() {
        return "Sorting(" + childMoveSelector + ")";
    }

}
