/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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

import java.util.ArrayList;
import java.util.List;

import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionCacheType;
import com.sankuai.optaplanner.core.impl.heuristic.move.Move;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.SelectionCacheLifecycleBridge;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.SelectionCacheLifecycleListener;
import com.sankuai.optaplanner.core.impl.heuristic.selector.move.AbstractMoveSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.move.MoveSelector;
import com.sankuai.optaplanner.core.impl.solver.scope.SolverScope;

public abstract class AbstractCachingMoveSelector<Solution_> extends AbstractMoveSelector<Solution_>
        implements SelectionCacheLifecycleListener<Solution_> {

    protected final MoveSelector<Solution_> childMoveSelector;
    protected final SelectionCacheType cacheType;

    protected List<Move<Solution_>> cachedMoveList = null;

    public AbstractCachingMoveSelector(MoveSelector<Solution_> childMoveSelector, SelectionCacheType cacheType) {
        this.childMoveSelector = childMoveSelector;
        this.cacheType = cacheType;
        if (childMoveSelector.isNeverEnding()) {
            throw new IllegalStateException("The selector (" + this
                    + ") has a childMoveSelector (" + childMoveSelector
                    + ") with neverEnding (" + childMoveSelector.isNeverEnding() + ").");
        }
        phaseLifecycleSupport.addEventListener(childMoveSelector);
        if (cacheType.isNotCached()) {
            throw new IllegalArgumentException("The selector (" + this
                    + ") does not support the cacheType (" + cacheType + ").");
        }
        phaseLifecycleSupport.addEventListener(new SelectionCacheLifecycleBridge<>(cacheType, this));
    }

    public MoveSelector<Solution_> getChildMoveSelector() {
        return childMoveSelector;
    }

    @Override
    public SelectionCacheType getCacheType() {
        return cacheType;
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public void constructCache(SolverScope<Solution_> solverScope) {
        long childSize = childMoveSelector.getSize();
        if (childSize > Integer.MAX_VALUE) {
            throw new IllegalStateException("The selector (" + this
                    + ") has a childMoveSelector (" + childMoveSelector
                    + ") with childSize (" + childSize
                    + ") which is higher than Integer.MAX_VALUE.");
        }
        cachedMoveList = new ArrayList<>((int) childSize);
        childMoveSelector.iterator().forEachRemaining(cachedMoveList::add);
        logger.trace("    Created cachedMoveList: size ({}), moveSelector ({}).",
                cachedMoveList.size(), this);
    }

    @Override
    public void disposeCache(SolverScope<Solution_> solverScope) {
        cachedMoveList = null;
    }

    @Override
    public boolean isCountable() {
        return true;
    }

    @Override
    public long getSize() {
        return cachedMoveList.size();
    }

}
