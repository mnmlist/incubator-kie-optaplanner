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

package com.sankuai.optaplanner.core.impl.score.stream.drools.uni;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.sankuai.optaplanner.core.api.function.QuadFunction;
import com.sankuai.optaplanner.core.api.function.TriFunction;
import com.sankuai.optaplanner.core.api.score.stream.bi.BiConstraintCollector;
import com.sankuai.optaplanner.core.api.score.stream.quad.QuadConstraintCollector;
import com.sankuai.optaplanner.core.api.score.stream.tri.TriConstraintCollector;
import com.sankuai.optaplanner.core.api.score.stream.uni.UniConstraintCollector;
import com.sankuai.optaplanner.core.impl.score.stream.drools.DroolsConstraintFactory;
import com.sankuai.optaplanner.core.impl.score.stream.drools.bi.DroolsAbstractBiConstraintStream;
import com.sankuai.optaplanner.core.impl.score.stream.drools.common.UniLeftHandSide;
import com.sankuai.optaplanner.core.impl.score.stream.drools.quad.DroolsAbstractQuadConstraintStream;
import com.sankuai.optaplanner.core.impl.score.stream.drools.tri.DroolsAbstractTriConstraintStream;

public final class DroolsGroupingUniConstraintStream<Solution_, NewA>
        extends DroolsAbstractUniConstraintStream<Solution_, NewA> {

    private final UniLeftHandSide<NewA> leftHandSide;

    public <A> DroolsGroupingUniConstraintStream(DroolsConstraintFactory<Solution_> constraintFactory,
            DroolsAbstractUniConstraintStream<Solution_, A> parent, Function<A, NewA> groupKeyMapping) {
        super(constraintFactory);
        this.leftHandSide = parent.getLeftHandSide().andGroupBy(groupKeyMapping);
    }

    public <A> DroolsGroupingUniConstraintStream(DroolsConstraintFactory<Solution_> constraintFactory,
            DroolsAbstractUniConstraintStream<Solution_, A> parent, UniConstraintCollector<A, ?, NewA> collector) {
        super(constraintFactory);
        this.leftHandSide = parent.getLeftHandSide().andGroupBy(collector);
    }

    public <A, B> DroolsGroupingUniConstraintStream(DroolsConstraintFactory<Solution_> constraintFactory,
            DroolsAbstractBiConstraintStream<Solution_, A, B> parent, BiFunction<A, B, NewA> groupKeyMapping) {
        super(constraintFactory);
        this.leftHandSide = parent.getLeftHandSide().andGroupBy(groupKeyMapping);
    }

    public <A, B> DroolsGroupingUniConstraintStream(DroolsConstraintFactory<Solution_> constraintFactory,
            DroolsAbstractBiConstraintStream<Solution_, A, B> parent, BiConstraintCollector<A, B, ?, NewA> collector) {
        super(constraintFactory);
        this.leftHandSide = parent.getLeftHandSide().andGroupBy(collector);
    }

    public <A, B, C> DroolsGroupingUniConstraintStream(DroolsConstraintFactory<Solution_> constraintFactory,
            DroolsAbstractTriConstraintStream<Solution_, A, B, C> parent,
            TriConstraintCollector<A, B, C, ?, NewA> collector) {
        super(constraintFactory);
        this.leftHandSide = parent.getLeftHandSide().andGroupBy(collector);
    }

    public <A, B, C> DroolsGroupingUniConstraintStream(DroolsConstraintFactory<Solution_> constraintFactory,
            DroolsAbstractTriConstraintStream<Solution_, A, B, C> parent, TriFunction<A, B, C, NewA> groupKeyMapping) {
        super(constraintFactory);
        this.leftHandSide = parent.getLeftHandSide().andGroupBy(groupKeyMapping);
    }

    public <A, B, C, D> DroolsGroupingUniConstraintStream(DroolsConstraintFactory<Solution_> constraintFactory,
            DroolsAbstractQuadConstraintStream<Solution_, A, B, C, D> parent,
            QuadConstraintCollector<A, B, C, D, ?, NewA> collector) {
        super(constraintFactory);
        this.leftHandSide = parent.getLeftHandSide().andGroupBy(collector);
    }

    public <A, B, C, D> DroolsGroupingUniConstraintStream(DroolsConstraintFactory<Solution_> constraintFactory,
            DroolsAbstractQuadConstraintStream<Solution_, A, B, C, D> parent,
            QuadFunction<A, B, C, D, NewA> groupKeyMapping) {
        super(constraintFactory);
        this.leftHandSide = parent.getLeftHandSide().andGroupBy(groupKeyMapping);
    }

    @Override
    public boolean guaranteesDistinct() {
        return true;
    }

    // ************************************************************************
    // Pattern creation
    // ************************************************************************

    @Override
    public UniLeftHandSide<NewA> getLeftHandSide() {
        return leftHandSide;
    }

    @Override
    public String toString() {
        return "UniGroup() with " + getChildStreams().size() + " children.";
    }
}
