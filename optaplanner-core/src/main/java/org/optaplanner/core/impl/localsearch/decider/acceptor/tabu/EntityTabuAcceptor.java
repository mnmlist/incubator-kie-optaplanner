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

package com.sankuai.optaplanner.core.impl.localsearch.decider.acceptor.tabu;

import java.util.Collection;

import com.sankuai.optaplanner.core.impl.localsearch.scope.LocalSearchMoveScope;
import com.sankuai.optaplanner.core.impl.localsearch.scope.LocalSearchStepScope;

public class EntityTabuAcceptor<Solution_> extends AbstractTabuAcceptor<Solution_> {

    public EntityTabuAcceptor(String logIndentation) {
        super(logIndentation);
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    protected Collection<? extends Object> findTabu(LocalSearchMoveScope<Solution_> moveScope) {
        return moveScope.getMove().getPlanningEntities();
    }

    @Override
    protected Collection<? extends Object> findNewTabu(LocalSearchStepScope<Solution_> stepScope) {
        return stepScope.getStep().getPlanningEntities();
    }

}
