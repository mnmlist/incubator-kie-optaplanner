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

package com.sankuai.optaplanner.persistence.jackson.api;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sankuai.optaplanner.core.api.score.Score;
import com.sankuai.optaplanner.core.api.score.buildin.bendable.BendableScore;
import com.sankuai.optaplanner.core.api.score.buildin.bendablebigdecimal.BendableBigDecimalScore;
import com.sankuai.optaplanner.core.api.score.buildin.bendablelong.BendableLongScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardmediumsoftbigdecimal.HardMediumSoftBigDecimalScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScore;
import com.sankuai.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import com.sankuai.optaplanner.core.api.score.buildin.simple.SimpleScore;
import com.sankuai.optaplanner.core.api.score.buildin.simplebigdecimal.SimpleBigDecimalScore;
import com.sankuai.optaplanner.core.api.score.buildin.simplelong.SimpleLongScore;
import com.sankuai.optaplanner.persistence.jackson.api.score.PolymorphicScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.PolymorphicScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.bendable.BendableScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.bendable.BendableScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.bendablebigdecimal.BendableBigDecimalScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.bendablebigdecimal.BendableBigDecimalScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.bendablelong.BendableLongScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.bendablelong.BendableLongScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardmediumsoft.HardMediumSoftScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardmediumsoft.HardMediumSoftScoreJsonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardmediumsoftbigdecimal.HardMediumSoftBigDecimalScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardmediumsoftbigdecimal.HardMediumSoftBigDecimalScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardsoft.HardSoftScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardsoft.HardSoftScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardsoftlong.HardSoftLongScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.hardsoftlong.HardSoftLongScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.simple.SimpleScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.simple.SimpleScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.simplebigdecimal.SimpleBigDecimalScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.simplebigdecimal.SimpleBigDecimalScoreJacksonSerializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.simplelong.SimpleLongScoreJacksonDeserializer;
import com.sankuai.optaplanner.persistence.jackson.api.score.buildin.simplelong.SimpleLongScoreJacksonSerializer;

/**
 * This class adds all Jackson serializers and deserializers.
 *
 */
public class OptaPlannerJacksonModule {

    /**
     * @return never null, register it with {@link ObjectMapper#registerModule(Module)}.
     */
    public static Module createModule() {
        SimpleModule module = new SimpleModule("OptaPlanner");

        // For non-subtype Score fields/properties, we also need to record the score type
        module.addSerializer(Score.class, new PolymorphicScoreJacksonSerializer());
        module.addDeserializer(Score.class, new PolymorphicScoreJacksonDeserializer());

        module.addSerializer(SimpleScore.class, new SimpleScoreJacksonSerializer());
        module.addDeserializer(SimpleScore.class, new SimpleScoreJacksonDeserializer());
        module.addSerializer(SimpleLongScore.class, new SimpleLongScoreJacksonSerializer());
        module.addDeserializer(SimpleLongScore.class, new SimpleLongScoreJacksonDeserializer());
        module.addSerializer(SimpleBigDecimalScore.class, new SimpleBigDecimalScoreJacksonSerializer());
        module.addDeserializer(SimpleBigDecimalScore.class, new SimpleBigDecimalScoreJacksonDeserializer());
        module.addSerializer(HardSoftScore.class, new HardSoftScoreJacksonSerializer());
        module.addDeserializer(HardSoftScore.class, new HardSoftScoreJacksonDeserializer());
        module.addSerializer(HardSoftLongScore.class, new HardSoftLongScoreJacksonSerializer());
        module.addDeserializer(HardSoftLongScore.class, new HardSoftLongScoreJacksonDeserializer());
        module.addSerializer(HardSoftBigDecimalScore.class, new HardSoftBigDecimalScoreJacksonSerializer());
        module.addDeserializer(HardSoftBigDecimalScore.class, new HardSoftBigDecimalScoreJacksonDeserializer());
        module.addSerializer(HardMediumSoftScore.class, new HardMediumSoftScoreJsonSerializer());
        module.addDeserializer(HardMediumSoftScore.class, new HardMediumSoftScoreJacksonDeserializer());
        module.addSerializer(HardMediumSoftLongScore.class, new HardMediumSoftLongScoreJacksonSerializer());
        module.addDeserializer(HardMediumSoftLongScore.class, new HardMediumSoftLongScoreJacksonDeserializer());
        module.addSerializer(HardMediumSoftBigDecimalScore.class, new HardMediumSoftBigDecimalScoreJacksonSerializer());
        module.addDeserializer(HardMediumSoftBigDecimalScore.class, new HardMediumSoftBigDecimalScoreJacksonDeserializer());
        module.addSerializer(BendableScore.class, new BendableScoreJacksonSerializer());
        module.addDeserializer(BendableScore.class, new BendableScoreJacksonDeserializer());
        module.addSerializer(BendableLongScore.class, new BendableLongScoreJacksonSerializer());
        module.addDeserializer(BendableLongScore.class, new BendableLongScoreJacksonDeserializer());
        module.addSerializer(BendableBigDecimalScore.class, new BendableBigDecimalScoreJacksonSerializer());
        module.addDeserializer(BendableBigDecimalScore.class, new BendableBigDecimalScoreJacksonDeserializer());

        return module;
    }

}
