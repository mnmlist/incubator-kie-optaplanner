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

package com.sankuai.optaplanner.spring.boot.autoconfigure.gizmo;

import com.sankuai.optaplanner.spring.boot.autoconfigure.gizmo.constraints.TestdataGizmoConstraintProvider;
import com.sankuai.optaplanner.spring.boot.autoconfigure.gizmo.domain.TestdataGizmoSpringEntity;
import com.sankuai.optaplanner.spring.boot.autoconfigure.gizmo.domain.TestdataGizmoSpringSolution;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackageClasses = { TestdataGizmoSpringEntity.class, TestdataGizmoSpringSolution.class,
        TestdataGizmoConstraintProvider.class })
public class GizmoSpringTestConfiguration {
}
