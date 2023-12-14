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

package com.sankuai.optaplanner.core.impl.heuristic.selector.value.decorator;

import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.sankuai.optaplanner.core.api.score.director.ScoreDirector;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionCacheType;
import com.sankuai.optaplanner.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.SelectionCacheLifecycleBridge;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.SelectionCacheLifecycleListener;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionProbabilityWeightFactory;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.AbstractValueSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.EntityIndependentValueSelector;
import com.sankuai.optaplanner.core.impl.solver.random.RandomUtils;
import com.sankuai.optaplanner.core.impl.solver.scope.SolverScope;

public class ProbabilityValueSelector<Solution_> extends AbstractValueSelector<Solution_>
        implements EntityIndependentValueSelector<Solution_>, SelectionCacheLifecycleListener<Solution_> {

    protected final EntityIndependentValueSelector<Solution_> childValueSelector;
    protected final SelectionCacheType cacheType;
    protected final SelectionProbabilityWeightFactory<Solution_, Object> probabilityWeightFactory;

    protected NavigableMap<Double, Object> cachedEntityMap = null;
    protected double probabilityWeightTotal = -1.0;

    public ProbabilityValueSelector(EntityIndependentValueSelector<Solution_> childValueSelector,
            SelectionCacheType cacheType,
            SelectionProbabilityWeightFactory<Solution_, Object> probabilityWeightFactory) {
        this.childValueSelector = childValueSelector;
        this.cacheType = cacheType;
        this.probabilityWeightFactory = probabilityWeightFactory;
        if (childValueSelector.isNeverEnding()) {
            throw new IllegalStateException("The selector (" + this
                    + ") has a childValueSelector (" + childValueSelector
                    + ") with neverEnding (" + childValueSelector.isNeverEnding() + ").");
        }
        phaseLifecycleSupport.addEventListener(childValueSelector);
        if (cacheType.isNotCached()) {
            throw new IllegalArgumentException("The selector (" + this
                    + ") does not support the cacheType (" + cacheType + ").");
        }
        phaseLifecycleSupport.addEventListener(new SelectionCacheLifecycleBridge<>(cacheType, this));
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
        cachedEntityMap = new TreeMap<>();
        ScoreDirector<Solution_> scoreDirector = solverScope.getScoreDirector();
        double probabilityWeightOffset = 0L;
        // TODO Fail-faster if a non FromSolutionPropertyValueSelector is used
        for (Object value : childValueSelector) {
            double probabilityWeight = probabilityWeightFactory.createProbabilityWeight(scoreDirector, value);
            cachedEntityMap.put(probabilityWeightOffset, value);
            probabilityWeightOffset += probabilityWeight;
        }
        probabilityWeightTotal = probabilityWeightOffset;
    }

    @Override
    public void disposeCache(SolverScope<Solution_> solverScope) {
        probabilityWeightTotal = -1.0;
    }

    @Override
    public GenuineVariableDescriptor<Solution_> getVariableDescriptor() {
        return childValueSelector.getVariableDescriptor();
    }

    @Override
    public boolean isCountable() {
        return true;
    }

    @Override
    public boolean isNeverEnding() {
        return false;
    }

    @Override
    public long getSize(Object entity) {
        return getSize();
    }

    @Override
    public long getSize() {
        return cachedEntityMap.size();
    }

    @Override
    public Iterator<Object> iterator(Object entity) {
        return iterator();
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                double randomOffset = RandomUtils.nextDouble(workingRandom, probabilityWeightTotal);
                Map.Entry<Double, Object> entry = cachedEntityMap.floorEntry(randomOffset);
                // entry is never null because randomOffset < probabilityWeightTotal
                return entry.getValue();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("The optional operation remove() is not supported.");
            }
        };
    }

    @Override
    public Iterator<Object> endingIterator(Object entity) {
        return childValueSelector.endingIterator(entity);
    }

    @Override
    public String toString() {
        return "Probability(" + childValueSelector + ")";
    }

}
