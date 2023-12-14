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

package com.sankuai.optaplanner.core.config.heuristic.selector.move;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.sankuai.optaplanner.core.config.heuristic.selector.SelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionCacheType;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.SelectionOrder;
import com.sankuai.optaplanner.core.config.heuristic.selector.common.decorator.SelectionSorterOrder;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.composite.CartesianProductMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.composite.UnionMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.factory.MoveIteratorFactoryConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.factory.MoveListFactoryConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.ChangeMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.PillarChangeMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.PillarSwapMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.SwapMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.chained.SubChainChangeMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.chained.SubChainSwapMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.generic.chained.TailChainSwapMoveSelectorConfig;
import com.sankuai.optaplanner.core.config.util.ConfigUtils;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionProbabilityWeightFactory;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorter;
import com.sankuai.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;

/**
 * General superclass for {@link ChangeMoveSelectorConfig}, etc.
 */

@XmlSeeAlso({
        UnionMoveSelectorConfig.class, CartesianProductMoveSelectorConfig.class, ChangeMoveSelectorConfig.class,
        SwapMoveSelectorConfig.class, PillarChangeMoveSelectorConfig.class, PillarSwapMoveSelectorConfig.class,
        TailChainSwapMoveSelectorConfig.class, SubChainChangeMoveSelectorConfig.class, SubChainSwapMoveSelectorConfig.class,
        MoveListFactoryConfig.class, MoveIteratorFactoryConfig.class })
@XmlType(propOrder = {
        "cacheType",
        "selectionOrder",
        "filterClass",
        "sorterComparatorClass",
        "sorterWeightFactoryClass",
        "sorterOrder",
        "sorterClass",
        "probabilityWeightFactoryClass",
        "selectedCountLimit",
        "fixedProbabilityWeight"
})
public abstract class MoveSelectorConfig<Config_ extends MoveSelectorConfig<Config_>> extends SelectorConfig<Config_> {

    protected SelectionCacheType cacheType = null;
    protected SelectionOrder selectionOrder = null;

    protected Class<? extends SelectionFilter> filterClass = null;

    protected Class<? extends Comparator> sorterComparatorClass = null;
    protected Class<? extends SelectionSorterWeightFactory> sorterWeightFactoryClass = null;
    protected SelectionSorterOrder sorterOrder = null;
    protected Class<? extends SelectionSorter> sorterClass = null;

    protected Class<? extends SelectionProbabilityWeightFactory> probabilityWeightFactoryClass = null;

    protected Long selectedCountLimit = null;

    private Double fixedProbabilityWeight = null;

    // ************************************************************************
    // Constructors and simple getters/setters
    // ************************************************************************

    public SelectionCacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(SelectionCacheType cacheType) {
        this.cacheType = cacheType;
    }

    public SelectionOrder getSelectionOrder() {
        return selectionOrder;
    }

    public void setSelectionOrder(SelectionOrder selectionOrder) {
        this.selectionOrder = selectionOrder;
    }

    public Class<? extends SelectionFilter> getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(Class<? extends SelectionFilter> filterClass) {
        this.filterClass = filterClass;
    }

    public Class<? extends Comparator> getSorterComparatorClass() {
        return sorterComparatorClass;
    }

    public void setSorterComparatorClass(Class<? extends Comparator> sorterComparatorClass) {
        this.sorterComparatorClass = sorterComparatorClass;
    }

    public Class<? extends SelectionSorterWeightFactory> getSorterWeightFactoryClass() {
        return sorterWeightFactoryClass;
    }

    public void setSorterWeightFactoryClass(Class<? extends SelectionSorterWeightFactory> sorterWeightFactoryClass) {
        this.sorterWeightFactoryClass = sorterWeightFactoryClass;
    }

    public SelectionSorterOrder getSorterOrder() {
        return sorterOrder;
    }

    public void setSorterOrder(SelectionSorterOrder sorterOrder) {
        this.sorterOrder = sorterOrder;
    }

    public Class<? extends SelectionSorter> getSorterClass() {
        return sorterClass;
    }

    public void setSorterClass(Class<? extends SelectionSorter> sorterClass) {
        this.sorterClass = sorterClass;
    }

    public Class<? extends SelectionProbabilityWeightFactory> getProbabilityWeightFactoryClass() {
        return probabilityWeightFactoryClass;
    }

    public void setProbabilityWeightFactoryClass(
            Class<? extends SelectionProbabilityWeightFactory> probabilityWeightFactoryClass) {
        this.probabilityWeightFactoryClass = probabilityWeightFactoryClass;
    }

    public Long getSelectedCountLimit() {
        return selectedCountLimit;
    }

    public void setSelectedCountLimit(Long selectedCountLimit) {
        this.selectedCountLimit = selectedCountLimit;
    }

    public Double getFixedProbabilityWeight() {
        return fixedProbabilityWeight;
    }

    public void setFixedProbabilityWeight(Double fixedProbabilityWeight) {
        this.fixedProbabilityWeight = fixedProbabilityWeight;
    }

    // ************************************************************************
    // With methods
    // ************************************************************************

    public MoveSelectorConfig<Config_> withCacheType(SelectionCacheType cacheType) {
        this.cacheType = cacheType;
        return this;
    }

    public MoveSelectorConfig<Config_> withSelectionOrder(SelectionOrder selectionOrder) {
        this.selectionOrder = selectionOrder;
        return this;
    }

    public MoveSelectorConfig<Config_> withFilterClass(Class<? extends SelectionFilter> filterClass) {
        this.filterClass = filterClass;
        return this;
    }

    public MoveSelectorConfig<Config_> withSorterComparatorClass(Class<? extends Comparator> sorterComparatorClass) {
        this.sorterComparatorClass = sorterComparatorClass;
        return this;
    }

    public MoveSelectorConfig<Config_> withSorterWeightFactoryClass(
            Class<? extends SelectionSorterWeightFactory> sorterWeightFactoryClass) {
        this.sorterWeightFactoryClass = sorterWeightFactoryClass;
        return this;
    }

    public MoveSelectorConfig<Config_> withSorterOrder(SelectionSorterOrder sorterOrder) {
        this.sorterOrder = sorterOrder;
        return this;
    }

    public MoveSelectorConfig<Config_> withSorterClass(Class<? extends SelectionSorter> sorterClass) {
        this.sorterClass = sorterClass;
        return this;
    }

    public MoveSelectorConfig<Config_> withProbabilityWeightFactoryClass(
            Class<? extends SelectionProbabilityWeightFactory> probabilityWeightFactoryClass) {
        this.probabilityWeightFactoryClass = probabilityWeightFactoryClass;
        return this;
    }

    public MoveSelectorConfig<Config_> withSelectedCountLimit(Long selectedCountLimit) {
        this.selectedCountLimit = selectedCountLimit;
        return this;
    }

    public MoveSelectorConfig<Config_> withFixedProbabilityWeight(Double fixedProbabilityWeight) {
        this.fixedProbabilityWeight = fixedProbabilityWeight;
        return this;
    }

    /**
     * Gather a list of all descendant {@link MoveSelectorConfig}s
     * except for {@link UnionMoveSelectorConfig} and {@link CartesianProductMoveSelectorConfig}.
     *
     * @param leafMoveSelectorConfigList not null
     */
    public void extractLeafMoveSelectorConfigsIntoList(List<MoveSelectorConfig> leafMoveSelectorConfigList) {
        leafMoveSelectorConfigList.add(this);
    }

    @Override
    public Config_ inherit(Config_ inheritedConfig) {
        inheritCommon(inheritedConfig);
        return (Config_) this;
    }

    /**
     * Does not inherit subclass properties because this class and {@code foldedConfig} can be of a different type.
     *
     * @param foldedConfig never null
     */
    public void inheritFolded(MoveSelectorConfig<Config_> foldedConfig) {
        inheritCommon(foldedConfig);
    }

    protected void visitCommonReferencedClasses(Consumer<Class<?>> classVisitor) {
        classVisitor.accept(filterClass);
        classVisitor.accept(sorterComparatorClass);
        classVisitor.accept(sorterWeightFactoryClass);
        classVisitor.accept(sorterClass);
        classVisitor.accept(probabilityWeightFactoryClass);
    }

    private void inheritCommon(MoveSelectorConfig<Config_> inheritedConfig) {
        cacheType = ConfigUtils.inheritOverwritableProperty(cacheType, inheritedConfig.getCacheType());
        selectionOrder = ConfigUtils.inheritOverwritableProperty(selectionOrder, inheritedConfig.getSelectionOrder());
        filterClass = ConfigUtils.inheritOverwritableProperty(filterClass, inheritedConfig.getFilterClass());
        sorterComparatorClass = ConfigUtils.inheritOverwritableProperty(
                sorterComparatorClass, inheritedConfig.getSorterComparatorClass());
        sorterWeightFactoryClass = ConfigUtils.inheritOverwritableProperty(
                sorterWeightFactoryClass, inheritedConfig.getSorterWeightFactoryClass());
        sorterOrder = ConfigUtils.inheritOverwritableProperty(
                sorterOrder, inheritedConfig.getSorterOrder());
        sorterClass = ConfigUtils.inheritOverwritableProperty(
                sorterClass, inheritedConfig.getSorterClass());
        probabilityWeightFactoryClass = ConfigUtils.inheritOverwritableProperty(
                probabilityWeightFactoryClass, inheritedConfig.getProbabilityWeightFactoryClass());
        selectedCountLimit = ConfigUtils.inheritOverwritableProperty(
                selectedCountLimit, inheritedConfig.getSelectedCountLimit());

        fixedProbabilityWeight = ConfigUtils.inheritOverwritableProperty(
                fixedProbabilityWeight, inheritedConfig.getFixedProbabilityWeight());
    }

}
