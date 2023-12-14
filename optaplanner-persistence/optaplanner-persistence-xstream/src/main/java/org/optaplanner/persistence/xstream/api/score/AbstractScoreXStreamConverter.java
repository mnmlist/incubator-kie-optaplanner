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

package com.sankuai.optaplanner.persistence.xstream.api.score;

import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.bendable.BendableScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.bendablebigdecimal.BendableBigDecimalScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.bendablelong.BendableLongScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.hardmediumsoft.HardMediumSoftScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.hardmediumsoftbigdecimal.HardMediumSoftBigDecimalScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.hardsoft.HardSoftScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.hardsoftlong.HardSoftLongScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.simple.SimpleScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.simplebigdecimal.SimpleBigDecimalScoreXStreamConverter;
import com.sankuai.optaplanner.persistence.xstream.api.score.buildin.simplelong.SimpleLongScoreXStreamConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;

public abstract class AbstractScoreXStreamConverter implements Converter {

    public static void registerScoreConverters(XStream xStream) {
        xStream.registerConverter(new SimpleScoreXStreamConverter());
        xStream.registerConverter(new SimpleLongScoreXStreamConverter());
        xStream.registerConverter(new SimpleBigDecimalScoreXStreamConverter());

        xStream.registerConverter(new HardSoftScoreXStreamConverter());
        xStream.registerConverter(new HardSoftLongScoreXStreamConverter());
        xStream.registerConverter(new HardSoftBigDecimalScoreXStreamConverter());

        xStream.registerConverter(new HardMediumSoftScoreXStreamConverter());
        xStream.registerConverter(new HardMediumSoftLongScoreXStreamConverter());
        xStream.registerConverter(new HardMediumSoftBigDecimalScoreXStreamConverter());

        xStream.registerConverter(new BendableScoreXStreamConverter());
        xStream.registerConverter(new BendableLongScoreXStreamConverter());
        xStream.registerConverter(new BendableBigDecimalScoreXStreamConverter());
    }

}
