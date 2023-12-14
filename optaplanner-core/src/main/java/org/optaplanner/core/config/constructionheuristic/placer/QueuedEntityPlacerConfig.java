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

package com.sankuai.optaplanner.core.config.constructionheuristic.placer;

import java.util.List;
import java.util.function.Consumer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import com.sankuai.optaplanner.core.config.heuristic.selector.entity.EntitySelectorConfig;
import com.sankuai.optaplanner.core.config.heuristic.selector.move.MoveSelectorConfig;
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

@XmlType(propOrder = {
        "entitySelectorConfig",
        "moveSelectorConfigList"
})
public class QueuedEntityPlacerConfig extends EntityPlacerConfig<QueuedEntityPlacerConfig> {

    @XmlElement(name = "entitySelector")
    protected EntitySelectorConfig entitySelectorConfig = null;

    @XmlElements({
            @XmlElement(name = CartesianProductMoveSelectorConfig.XML_ELEMENT_NAME,
                    type = CartesianProductMoveSelectorConfig.class),
            @XmlElement(name = ChangeMoveSelectorConfig.XML_ELEMENT_NAME, type = ChangeMoveSelectorConfig.class),
            @XmlElement(name = MoveIteratorFactoryConfig.XML_ELEMENT_NAME, type = MoveIteratorFactoryConfig.class),
            @XmlElement(name = MoveListFactoryConfig.XML_ELEMENT_NAME, type = MoveListFactoryConfig.class),
            @XmlElement(name = PillarChangeMoveSelectorConfig.XML_ELEMENT_NAME,
                    type = PillarChangeMoveSelectorConfig.class),
            @XmlElement(name = PillarSwapMoveSelectorConfig.XML_ELEMENT_NAME, type = PillarSwapMoveSelectorConfig.class),
            @XmlElement(name = SubChainChangeMoveSelectorConfig.XML_ELEMENT_NAME,
                    type = SubChainChangeMoveSelectorConfig.class),
            @XmlElement(name = SubChainSwapMoveSelectorConfig.XML_ELEMENT_NAME,
                    type = SubChainSwapMoveSelectorConfig.class),
            @XmlElement(name = SwapMoveSelectorConfig.XML_ELEMENT_NAME, type = SwapMoveSelectorConfig.class),
            @XmlElement(name = TailChainSwapMoveSelectorConfig.XML_ELEMENT_NAME,
                    type = TailChainSwapMoveSelectorConfig.class),
            @XmlElement(name = UnionMoveSelectorConfig.XML_ELEMENT_NAME, type = UnionMoveSelectorConfig.class)
    })
    protected List<MoveSelectorConfig> moveSelectorConfigList = null;

    public EntitySelectorConfig getEntitySelectorConfig() {
        return entitySelectorConfig;
    }

    public void setEntitySelectorConfig(EntitySelectorConfig entitySelectorConfig) {
        this.entitySelectorConfig = entitySelectorConfig;
    }

    public List<MoveSelectorConfig> getMoveSelectorConfigList() {
        return moveSelectorConfigList;
    }

    public void setMoveSelectorConfigList(List<MoveSelectorConfig> moveSelectorConfigList) {
        this.moveSelectorConfigList = moveSelectorConfigList;
    }

    @Override
    public QueuedEntityPlacerConfig inherit(QueuedEntityPlacerConfig inheritedConfig) {
        entitySelectorConfig = ConfigUtils.inheritConfig(entitySelectorConfig, inheritedConfig.getEntitySelectorConfig());
        moveSelectorConfigList = ConfigUtils.inheritMergeableListConfig(
                moveSelectorConfigList, inheritedConfig.getMoveSelectorConfigList());
        return this;
    }

    @Override
    public QueuedEntityPlacerConfig copyConfig() {
        return new QueuedEntityPlacerConfig().inherit(this);
    }

    @Override
    public void visitReferencedClasses(Consumer<Class<?>> classVisitor) {
        if (entitySelectorConfig != null) {
            entitySelectorConfig.visitReferencedClasses(classVisitor);
        }
        if (moveSelectorConfigList != null) {
            moveSelectorConfigList.forEach(ms -> ms.visitReferencedClasses(classVisitor));
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + entitySelectorConfig + ", " + moveSelectorConfigList + ")";
    }

}
