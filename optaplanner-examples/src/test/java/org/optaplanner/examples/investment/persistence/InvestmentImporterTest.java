/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package com.sankuai.optaplanner.examples.investment.persistence;

import com.sankuai.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import com.sankuai.optaplanner.examples.common.persistence.ImportDataFilesTest;
import com.sankuai.optaplanner.examples.investment.app.InvestmentApp;
import com.sankuai.optaplanner.examples.investment.domain.InvestmentSolution;

public class InvestmentImporterTest extends ImportDataFilesTest<InvestmentSolution> {

    @Override
    protected AbstractSolutionImporter<InvestmentSolution> createSolutionImporter() {
        return new InvestmentImporter();
    }

    @Override
    protected String getDataDirName() {
        return InvestmentApp.DATA_DIR_NAME;
    }
}
