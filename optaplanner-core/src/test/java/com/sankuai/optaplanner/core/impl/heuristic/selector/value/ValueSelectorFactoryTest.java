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

package com.sankuai.optaplanner.core.impl.heuristic.selector.value;

import com.sankuai.optaplanner.core.api.score.director.ScoreDirector;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionCacheType;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionOrder;
import com.sankuai.optaplanner.core.config.heuristic.selector.value.ValueSelectorConfig;
import com.sankuai.optaplanner.core.impl.domain.entity.descriptor.EntityDescriptor;
import com.sankuai.optaplanner.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;
import com.sankuai.optaplanner.core.impl.heuristic.HeuristicConfigPolicy;
import com.sankuai.optaplanner.core.impl.heuristic.selector.AbstractSelectorFactoryTest;
import com.sankuai.optaplanner.core.impl.heuristic.selector.SelectorTestUtils;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionProbabilityWeightFactory;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.decorator.FilteringValueSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.decorator.ProbabilityValueSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.decorator.ShufflingValueSelector;
import com.sankuai.optaplanner.core.impl.heuristic.selector.value.decorator.SortingValueSelector;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataSolution;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValueSelectorFactoryTest extends AbstractSelectorFactoryTest {

    @Test
    void phaseOriginal() {
        HeuristicConfigPolicy configPolicy = buildHeuristicConfigPolicy();
        EntityDescriptor entityDescriptor = configPolicy.getSolutionDescriptor()
                .findEntityDescriptorOrFail(TestdataEntity.class);
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setCacheType(SelectionCacheType.PHASE);
        valueSelectorConfig.setSelectionOrder(SelectionOrder.ORIGINAL);
        ValueSelector valueSelector = ValueSelectorFactory.create(valueSelectorConfig).buildValueSelector(configPolicy,
                entityDescriptor, SelectionCacheType.JUST_IN_TIME, SelectionOrder.RANDOM);
        assertThat(valueSelector).isInstanceOf(FromSolutionPropertyValueSelector.class)
                .isNotInstanceOf(ShufflingValueSelector.class);
        assertThat(valueSelector.getCacheType()).isEqualTo(SelectionCacheType.PHASE);
    }

    @Test
    void stepOriginal() {
        HeuristicConfigPolicy configPolicy = buildHeuristicConfigPolicy();
        EntityDescriptor entityDescriptor = configPolicy.getSolutionDescriptor()
                .findEntityDescriptorOrFail(TestdataEntity.class);
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setCacheType(SelectionCacheType.STEP);
        valueSelectorConfig.setSelectionOrder(SelectionOrder.ORIGINAL);
        ValueSelector valueSelector = ValueSelectorFactory.create(valueSelectorConfig).buildValueSelector(configPolicy,
                entityDescriptor, SelectionCacheType.JUST_IN_TIME, SelectionOrder.RANDOM);
        assertThat(valueSelector).isInstanceOf(FromSolutionPropertyValueSelector.class)
                .isNotInstanceOf(ShufflingValueSelector.class);
        // PHASE instead of STEP because these values are cacheable, so there's no reason not to cache them?
        assertThat(valueSelector.getCacheType()).isEqualTo(SelectionCacheType.PHASE);
    }

    @Test
    void justInTimeOriginal() {
        HeuristicConfigPolicy configPolicy = buildHeuristicConfigPolicy();
        EntityDescriptor entityDescriptor = configPolicy.getSolutionDescriptor()
                .findEntityDescriptorOrFail(TestdataEntity.class);
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setCacheType(SelectionCacheType.JUST_IN_TIME);
        valueSelectorConfig.setSelectionOrder(SelectionOrder.ORIGINAL);
        ValueSelector valueSelector = ValueSelectorFactory.create(valueSelectorConfig).buildValueSelector(configPolicy,
                entityDescriptor, SelectionCacheType.JUST_IN_TIME, SelectionOrder.RANDOM);
        assertThat(valueSelector).isInstanceOf(FromSolutionPropertyValueSelector.class);
        // cacheType gets upgraded to STEP
        // assertEquals(SelectionCacheType.JUST_IN_TIME, valueSelector.getCacheType());
    }

    @Test
    void phaseRandom() {
        HeuristicConfigPolicy configPolicy = buildHeuristicConfigPolicy();
        EntityDescriptor entityDescriptor = configPolicy.getSolutionDescriptor()
                .findEntityDescriptorOrFail(TestdataEntity.class);
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setCacheType(SelectionCacheType.PHASE);
        valueSelectorConfig.setSelectionOrder(SelectionOrder.RANDOM);
        ValueSelector valueSelector = ValueSelectorFactory.create(valueSelectorConfig).buildValueSelector(
                configPolicy, entityDescriptor,
                SelectionCacheType.JUST_IN_TIME, SelectionOrder.RANDOM);
        assertThat(valueSelector)
                .isInstanceOf(FromSolutionPropertyValueSelector.class);
        assertThat(valueSelector)
                .isNotInstanceOf(ShufflingValueSelector.class);
        assertThat(valueSelector.getCacheType()).isEqualTo(SelectionCacheType.PHASE);
    }

    @Test
    void stepRandom() {
        HeuristicConfigPolicy configPolicy = buildHeuristicConfigPolicy();
        EntityDescriptor entityDescriptor = configPolicy.getSolutionDescriptor()
                .findEntityDescriptorOrFail(TestdataEntity.class);
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setCacheType(SelectionCacheType.STEP);
        valueSelectorConfig.setSelectionOrder(SelectionOrder.RANDOM);
        ValueSelector valueSelector = ValueSelectorFactory.create(valueSelectorConfig).buildValueSelector(configPolicy,
                entityDescriptor, SelectionCacheType.JUST_IN_TIME, SelectionOrder.RANDOM);
        assertThat(valueSelector).isInstanceOf(FromSolutionPropertyValueSelector.class)
                .isNotInstanceOf(ShufflingValueSelector.class);
        // PHASE instead of STEP because these values are cacheable, so there's no reason not to cache them?
        assertThat(valueSelector.getCacheType()).isEqualTo(SelectionCacheType.PHASE);
    }

    @Test
    void justInTimeRandom() {
        HeuristicConfigPolicy configPolicy = buildHeuristicConfigPolicy();
        EntityDescriptor entityDescriptor = configPolicy.getSolutionDescriptor()
                .findEntityDescriptorOrFail(TestdataEntity.class);
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setCacheType(SelectionCacheType.JUST_IN_TIME);
        valueSelectorConfig.setSelectionOrder(SelectionOrder.RANDOM);
        ValueSelector valueSelector = ValueSelectorFactory.create(valueSelectorConfig).buildValueSelector(configPolicy,
                entityDescriptor, SelectionCacheType.JUST_IN_TIME, SelectionOrder.RANDOM);
        assertThat(valueSelector).isInstanceOf(FromSolutionPropertyValueSelector.class);
        // cacheType gets upgraded to STEP
        // assertEquals(SelectionCacheType.JUST_IN_TIME, valueSelector.getCacheType());
    }

    @Test
    void phaseShuffled() {
        HeuristicConfigPolicy configPolicy = buildHeuristicConfigPolicy();
        EntityDescriptor entityDescriptor = configPolicy.getSolutionDescriptor()
                .findEntityDescriptorOrFail(TestdataEntity.class);
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setCacheType(SelectionCacheType.PHASE);
        valueSelectorConfig.setSelectionOrder(SelectionOrder.SHUFFLED);
        ValueSelector valueSelector = ValueSelectorFactory.create(valueSelectorConfig).buildValueSelector(configPolicy,
                entityDescriptor, SelectionCacheType.JUST_IN_TIME, SelectionOrder.RANDOM);
        assertThat(valueSelector).isInstanceOf(ShufflingValueSelector.class);
        assertThat(((ShufflingValueSelector) valueSelector).getChildValueSelector())
                .isInstanceOf(FromSolutionPropertyValueSelector.class);
        assertThat(valueSelector.getCacheType()).isEqualTo(SelectionCacheType.PHASE);
    }

    @Test
    void stepShuffled() {
        HeuristicConfigPolicy configPolicy = buildHeuristicConfigPolicy();
        EntityDescriptor entityDescriptor = configPolicy.getSolutionDescriptor()
                .findEntityDescriptorOrFail(TestdataEntity.class);
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setCacheType(SelectionCacheType.STEP);
        valueSelectorConfig.setSelectionOrder(SelectionOrder.SHUFFLED);
        ValueSelector valueSelector = ValueSelectorFactory.create(valueSelectorConfig).buildValueSelector(configPolicy,
                entityDescriptor, SelectionCacheType.JUST_IN_TIME, SelectionOrder.RANDOM);
        assertThat(valueSelector).isInstanceOf(ShufflingValueSelector.class);
        assertThat(((ShufflingValueSelector) valueSelector).getChildValueSelector())
                .isInstanceOf(FromSolutionPropertyValueSelector.class);
        assertThat(valueSelector.getCacheType()).isEqualTo(SelectionCacheType.STEP);
    }

    @Test
    void justInTimeShuffled() {
        HeuristicConfigPolicy configPolicy = buildHeuristicConfigPolicy();
        EntityDescriptor entityDescriptor =
                configPolicy.getSolutionDescriptor().findEntityDescriptorOrFail(TestdataEntity.class);
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setCacheType(SelectionCacheType.JUST_IN_TIME);
        valueSelectorConfig.setSelectionOrder(SelectionOrder.SHUFFLED);
        assertThatIllegalArgumentException().isThrownBy(() -> ValueSelectorFactory.create(valueSelectorConfig)
                .buildValueSelector(configPolicy, entityDescriptor, SelectionCacheType.JUST_IN_TIME, SelectionOrder.RANDOM));
    }

    @Test
    void applyFiltering_withFilterClass() {
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setFilterClass(DummyValueFilter.class);

        ValueSelector baseValueSelector =
                SelectorTestUtils.mockValueSelector(TestdataEntity.class, "value", new TestdataValue("v1"));
        ValueSelector resultingValueSelector =
                ValueSelectorFactory.create(valueSelectorConfig).applyFiltering(baseValueSelector);
        assertThat(resultingValueSelector).isExactlyInstanceOf(FilteringValueSelector.class);
    }

    @Test
    void applyProbability_withSelectionProbabilityWeightFactory() {
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setProbabilityWeightFactoryClass(DummySelectionProbabilityWeightFactory.class);

        ValueSelector baseValueSelector = mock(EntityIndependentValueSelector.class);
        ValueSelectorFactory valueSelectorFactory = ValueSelectorFactory.create(valueSelectorConfig);
        valueSelectorFactory.validateProbability(SelectionOrder.PROBABILISTIC);
        ValueSelector resultingValueSelector =
                valueSelectorFactory.applyProbability(SelectionCacheType.PHASE, SelectionOrder.PROBABILISTIC,
                        baseValueSelector);
        assertThat(resultingValueSelector).isExactlyInstanceOf(ProbabilityValueSelector.class);
    }

    @Test
    void applySorting_withSorterComparatorClass() {
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setSorterComparatorClass(DummyValueComparator.class);
        applySorting(valueSelectorConfig);
    }

    @Test
    void applySorting_withSorterWeightFactoryClass() {
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setSorterWeightFactoryClass(DummySelectionSorterWeightFactory.class);
        applySorting(valueSelectorConfig);
    }

    private void applySorting(ValueSelectorConfig valueSelectorConfig) {
        ValueSelectorFactory valueSelectorFactory = ValueSelectorFactory.create(valueSelectorConfig);
        valueSelectorFactory.validateSorting(SelectionOrder.SORTED);

        ValueSelector baseValueSelector = mock(EntityIndependentValueSelector.class);
        GenuineVariableDescriptor variableDescriptor = mock(GenuineVariableDescriptor.class);
        when(baseValueSelector.getVariableDescriptor()).thenReturn(variableDescriptor);
        when(variableDescriptor.isValueRangeEntityIndependent()).thenReturn(true);

        ValueSelector resultingValueSelector =
                valueSelectorFactory.applySorting(SelectionCacheType.PHASE, SelectionOrder.SORTED, baseValueSelector);
        assertThat(resultingValueSelector).isExactlyInstanceOf(SortingValueSelector.class);
    }

    @Test
    void applySortingFailsFast_withoutAnySorter() {
        ValueSelectorFactory valueSelectorFactory = ValueSelectorFactory.create(new ValueSelectorConfig());
        ValueSelector baseValueSelector = mock(ValueSelector.class);
        assertThatIllegalArgumentException().isThrownBy(
                () -> valueSelectorFactory.applySorting(SelectionCacheType.PHASE, SelectionOrder.SORTED, baseValueSelector))
                .withMessageContaining("needs a sorterManner");
    }

    @Test
    void failFast_ifMimicRecordingIsUsedWithOtherProperty() {
        ValueSelectorConfig valueSelectorConfig = new ValueSelectorConfig();
        valueSelectorConfig.setSelectedCountLimit(10L);
        valueSelectorConfig.setMimicSelectorRef("someSelectorId");

        assertThatIllegalArgumentException().isThrownBy(
                () -> ValueSelectorFactory.create(valueSelectorConfig).buildMimicReplaying(mock(HeuristicConfigPolicy.class)))
                .withMessageContaining("has another property");
    }

    public static class DummyValueFilter implements SelectionFilter<TestdataSolution, TestdataValue> {
        @Override
        public boolean accept(ScoreDirector<TestdataSolution> scoreDirector, TestdataValue selection) {
            return true;
        }
    }

    public static class DummySelectionProbabilityWeightFactory
            implements SelectionProbabilityWeightFactory<TestdataSolution, TestdataValue> {

        @Override
        public double createProbabilityWeight(ScoreDirector<TestdataSolution> scoreDirector, TestdataValue selection) {
            return 0.0;
        }
    }

    public static class DummySelectionSorterWeightFactory
            implements SelectionSorterWeightFactory<TestdataSolution, TestdataValue> {
        @Override
        public Comparable createSorterWeight(TestdataSolution testdataSolution, TestdataValue selection) {
            return 0;
        }
    }

    public static class DummyValueComparator implements Comparator<TestdataValue> {
        @Override
        public int compare(TestdataValue testdataValue, TestdataValue testdataValue2) {
            return 0;
        }
    }
}
