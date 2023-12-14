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

import java.util.Collections;
import java.util.Iterator;

import com.sankuai.optaplanner.core.api.score.director.ScoreDirector;
import com.sankuai.optaplanner.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.AbstractValueSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.EntityIndependentValueSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.ValueSelector;
import com.sankuai.optaplanner.core.impl.phase.scope.AbstractPhaseScope;

/**
 * Prevents reassigning of already initialized variables during Construction Heuristics and Exhaustive Search.
 * <p>
 * Returns no values for an entity's variable if the variable is already initialized.
 * <p>
 * Does not implement {@link EntityIndependentValueSelector} because if used like that,
 * it shouldn't be added during configuration in the first place.
 */
public class ReinitializeVariableValueSelector<Solution_> extends AbstractValueSelector<Solution_> {

    protected final ValueSelector<Solution_> childValueSelector;
    protected final SelectionFilter<Solution_, Object> reinitializeVariableEntityFilter;

    protected ScoreDirector<Solution_> scoreDirector = null;

    public ReinitializeVariableValueSelector(ValueSelector<Solution_> childValueSelector) {
        this.childValueSelector = childValueSelector;
        this.reinitializeVariableEntityFilter = childValueSelector.getVariableDescriptor()
                .getReinitializeVariableEntityFilter();
        phaseLifecycleSupport.addEventListener(childValueSelector);
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public void phaseStarted(AbstractPhaseScope<Solution_> phaseScope) {
        super.phaseStarted(phaseScope);
        scoreDirector = phaseScope.getScoreDirector();
    }

    @Override
    public void phaseEnded(AbstractPhaseScope<Solution_> phaseScope) {
        super.phaseEnded(phaseScope);
        scoreDirector = null;
    }

    @Override
    public GenuineVariableDescriptor<Solution_> getVariableDescriptor() {
        return childValueSelector.getVariableDescriptor();
    }

    @Override
    public boolean isCountable() {
        return childValueSelector.isCountable();
    }

    @Override
    public boolean isNeverEnding() {
        return childValueSelector.isNeverEnding();
    }

    @Override
    public long getSize(Object entity) {
        if (!reinitializeVariableEntityFilter.accept(scoreDirector, entity)) {
            return 0L;
        }
        return childValueSelector.getSize(entity);
    }

    @Override
    public Iterator<Object> iterator(Object entity) {
        if (!reinitializeVariableEntityFilter.accept(scoreDirector, entity)) {
            return Collections.emptyIterator();
        }
        return childValueSelector.iterator(entity);
    }

    @Override
    public Iterator<Object> endingIterator(Object entity) {
        if (!reinitializeVariableEntityFilter.accept(scoreDirector, entity)) {
            return Collections.emptyIterator();
        }
        return childValueSelector.endingIterator(entity);
    }

    @Override
    public String toString() {
        return "Reinitialize(" + childValueSelector + ")";
    }

}
