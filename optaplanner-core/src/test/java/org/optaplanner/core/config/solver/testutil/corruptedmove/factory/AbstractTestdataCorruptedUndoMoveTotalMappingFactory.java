package com.sankuai.optaplanner.core.config.solver.testutil.corruptedmove.factory;

import java.util.ArrayList;
import java.util.List;

import com.sankuai.optaplanner.core.config.solver.testutil.corruptedmove.AbstractTestdataMove;
import com.sankuai.optaplanner.core.config.solver.testutil.corruptedmove.TestdataCorruptedEntityUndoMove;
import com.sankuai.optaplanner.core.config.solver.testutil.corruptedmove.TestdataCorruptedUndoMove;
import com.sankuai.optaplanner.core.impl.heuristic.move.Move;
import com.sankuai.optaplanner.core.impl.heuristic.selector.move.factory.MoveListFactory;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataEntity;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataSolution;
import com.sankuai.optaplanner.core.impl.testdata.domain.TestdataValue;

public class AbstractTestdataCorruptedUndoMoveTotalMappingFactory implements MoveListFactory<TestdataSolution> {

    private boolean corruptEntityAsWell;

    AbstractTestdataCorruptedUndoMoveTotalMappingFactory(boolean corruptEntityAsWell) {
        this.corruptEntityAsWell = corruptEntityAsWell;
    }

    @Override
    public List<? extends Move<TestdataSolution>> createMoveList(TestdataSolution solution) {
        List<AbstractTestdataMove> moveList = new ArrayList<>();

        for (TestdataEntity entity : solution.getEntityList()) {
            for (TestdataValue value : solution.getValueList()) {
                if (corruptEntityAsWell) {
                    moveList.add(new TestdataCorruptedEntityUndoMove(entity, value));
                } else {
                    moveList.add(new TestdataCorruptedUndoMove(entity, value));
                }
            }
        }
        return moveList;
    }
}
