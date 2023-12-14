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

package com.sankuai.optaplanner.core.impl.domain.variable.listener.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import com.sankuai.optaplanner.core.impl.domain.variable.descriptor.VariableDescriptor;
import com.sankuai.optaplanner.core.impl.domain.variable.inverserelation.ExternalizedSingletonInverseVariableSupply;
import com.sankuai.optaplanner.core.impl.domain.variable.inverserelation.SingletonInverseVariableDemand;
import com.sankuai.optaplanner.core.impl.domain.variable.inverserelation.SingletonInverseVariableListener;
import com.sankuai.optaplanner.core.impl.domain.variable.inverserelation.SingletonInverseVariableSupply;
import com.sankuai.optaplanner.core.impl.domain.variable.supply.SupplyManager;
import com.sankuai.optaplanner.core.impl.score.director.InnerScoreDirector;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataSolution;
import com.sankuai.optaplanner.core.impl.testdata.domain.chained.TestdataChainedEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.chained.TestdataChainedSolution;
import com.sankuai.optaplanner.core.impl.testdata.domain.chained.shadow.TestdataShadowingChainedEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.chained.shadow.TestdataShadowingChainedSolution;

public class VariableListenerSupportTest {

    @Test
    public void demandBasic() {
        SolutionDescriptor<TestdataSolution> solutionDescriptor = TestdataSolution.buildSolutionDescriptor();
        InnerScoreDirector<TestdataSolution, SimpleScore> scoreDirector = mock(InnerScoreDirector.class);
        when(scoreDirector.getSolutionDescriptor()).thenReturn(solutionDescriptor);
        TestdataSolution solution = new TestdataSolution();
        solution.setEntityList(Collections.emptyList());
        when(scoreDirector.getWorkingSolution()).thenReturn(solution);
        when(scoreDirector.getSupplyManager()).thenReturn(mock(SupplyManager.class));
        VariableListenerSupport<TestdataSolution> variableListenerSupport = new VariableListenerSupport<>(scoreDirector);
        variableListenerSupport.linkVariableListeners();

        VariableDescriptor<TestdataSolution> variableDescriptor =
                solutionDescriptor.getEntityDescriptorStrict(TestdataEntity.class)
                        .getVariableDescriptor("value");

        SingletonInverseVariableSupply supply1 = variableListenerSupport
                .demand(new SingletonInverseVariableDemand<>(variableDescriptor));
        SingletonInverseVariableSupply supply2 = variableListenerSupport
                .demand(new SingletonInverseVariableDemand<>(variableDescriptor));
        assertThat(supply2).isSameAs(supply1);
    }

    @Test
    public void demandChained() {
        SolutionDescriptor<TestdataChainedSolution> solutionDescriptor = TestdataChainedSolution.buildSolutionDescriptor();
        InnerScoreDirector<TestdataChainedSolution, SimpleScore> scoreDirector = mock(InnerScoreDirector.class);
        when(scoreDirector.getSolutionDescriptor()).thenReturn(solutionDescriptor);
        TestdataChainedSolution solution = new TestdataChainedSolution();
        solution.setChainedEntityList(Collections.emptyList());
        when(scoreDirector.getWorkingSolution()).thenReturn(solution);
        when(scoreDirector.getSupplyManager()).thenReturn(mock(SupplyManager.class));
        VariableListenerSupport<TestdataChainedSolution> variableListenerSupport = new VariableListenerSupport<>(scoreDirector);
        variableListenerSupport.linkVariableListeners();

        VariableDescriptor<TestdataChainedSolution> variableDescriptor =
                solutionDescriptor.getEntityDescriptorStrict(TestdataChainedEntity.class)
                        .getVariableDescriptor("chainedObject");

        SingletonInverseVariableSupply supply1 = variableListenerSupport
                .demand(new SingletonInverseVariableDemand<>(variableDescriptor));
        assertThat(supply1)
                .isInstanceOf(ExternalizedSingletonInverseVariableSupply.class);
        SingletonInverseVariableSupply supply2 = variableListenerSupport
                .demand(new SingletonInverseVariableDemand<>(variableDescriptor));
        assertThat(supply2).isSameAs(supply1);
    }

    @Test
    public void demandRichChained() {
        SolutionDescriptor<TestdataShadowingChainedSolution> solutionDescriptor =
                TestdataShadowingChainedSolution.buildSolutionDescriptor();
        InnerScoreDirector<TestdataShadowingChainedSolution, SimpleScore> scoreDirector =
                mock(InnerScoreDirector.class);
        when(scoreDirector.getSolutionDescriptor()).thenReturn(solutionDescriptor);
        TestdataShadowingChainedSolution solution = new TestdataShadowingChainedSolution();
        solution.setChainedEntityList(Collections.emptyList());
        when(scoreDirector.getWorkingSolution()).thenReturn(solution);
        when(scoreDirector.getSupplyManager()).thenReturn(mock(SupplyManager.class));
        VariableListenerSupport<TestdataShadowingChainedSolution> variableListenerSupport =
                new VariableListenerSupport<>(scoreDirector);
        variableListenerSupport.linkVariableListeners();

        VariableDescriptor<TestdataShadowingChainedSolution> variableDescriptor = solutionDescriptor
                .getEntityDescriptorStrict(TestdataShadowingChainedEntity.class)
                .getVariableDescriptor("chainedObject");

        SingletonInverseVariableSupply supply1 = variableListenerSupport
                .demand(new SingletonInverseVariableDemand<>(variableDescriptor));
        assertThat(supply1)
                .isInstanceOf(SingletonInverseVariableListener.class);
        SingletonInverseVariableSupply supply2 = variableListenerSupport
                .demand(new SingletonInverseVariableDemand<>(variableDescriptor));
        assertThat(supply2).isSameAs(supply1);
    }

}
