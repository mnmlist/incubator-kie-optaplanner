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

import com.sankuai.optaplanner.core.impl.heuristic.selector.value.EntityIndependentValueSelector;

public class EntityIndependentInitializedValueSelector<Solution_> extends InitializedValueSelector<Solution_>
        implements EntityIndependentValueSelector<Solution_> {

    public EntityIndependentInitializedValueSelector(EntityIndependentValueSelector<Solution_> childValueSelector) {
        super(childValueSelector);
    }

    @Override
    public long getSize() {
        return ((EntityIndependentValueSelector<Solution_>) childValueSelector).getSize();
    }

    @Override
    public Iterator<Object> iterator() {
        return new JustInTimeInitializedValueIterator(
                ((EntityIndependentValueSelector<Solution_>) childValueSelector).iterator(), determineBailOutSize());
    }

    protected long determineBailOutSize() {
        if (!bailOutEnabled) {
            return -1L;
        }
        return ((EntityIndependentValueSelector<Solution_>) childValueSelector).getSize() * 10L;
    }

}
