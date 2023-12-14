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

package com.sankuai.optaplanner.persistence.jpa.api.score.buildin.hardsoft;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sankuai.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@Converter
public class HardSoftScoreConverter implements AttributeConverter<HardSoftScore, String> {

    @Override
    public String convertToDatabaseColumn(HardSoftScore score) {
        if (score == null) {
            return null;
        }

        return score.toString();
    }

    @Override
    public HardSoftScore convertToEntityAttribute(String scoreString) {
        if (scoreString == null) {
            return null;
        }

        return HardSoftScore.parseScore(scoreString);
    }
}
