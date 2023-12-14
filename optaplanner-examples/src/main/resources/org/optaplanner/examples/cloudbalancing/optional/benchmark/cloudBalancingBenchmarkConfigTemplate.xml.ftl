<?xml version="1.0" encoding="UTF-8"?>
<plannerBenchmark>
  <benchmarkDirectory>local/data/cloudbalancing/template</benchmarkDirectory>
  <parallelBenchmarkCount>AUTO</parallelBenchmarkCount>

  <inheritedSolverBenchmark>
    <problemBenchmarks>
      <solutionFileIOClass>com.sankuai.optaplanner.examples.cloudbalancing.persistence.CloudBalanceXmlSolutionFileIO</solutionFileIOClass>
      <!--<inputSolutionFile>data/cloudbalancing/unsolved/2computers-6processes.xml</inputSolutionFile>-->
      <!--<inputSolutionFile>data/cloudbalancing/unsolved/3computers-9processes.xml</inputSolutionFile>-->
      <!--<inputSolutionFile>data/cloudbalancing/unsolved/4computers-12processes.xml</inputSolutionFile>-->
      <inputSolutionFile>data/cloudbalancing/unsolved/100computers-300processes.xml</inputSolutionFile>
      <inputSolutionFile>data/cloudbalancing/unsolved/200computers-600processes.xml</inputSolutionFile>
      <inputSolutionFile>data/cloudbalancing/unsolved/400computers-1200processes.xml</inputSolutionFile>
      <inputSolutionFile>data/cloudbalancing/unsolved/800computers-2400processes.xml</inputSolutionFile>
    </problemBenchmarks>

    <solver>
      <solutionClass>com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudBalance</solutionClass>
      <entityClass>com.sankuai.optaplanner.examples.cloudbalancing.domain.CloudProcess</entityClass>
      <scoreDirectorFactory>
        <scoreDrl>org/optaplanner/examples/cloudbalancing/solver/cloudBalancingConstraints.drl</scoreDrl>
        <initializingScoreTrend>ONLY_DOWN/ONLY_DOWN</initializingScoreTrend>
      </scoreDirectorFactory>
      <termination>
        <minutesSpentLimit>5</minutesSpentLimit>
      </termination>
      <constructionHeuristic>
        <constructionHeuristicType>FIRST_FIT_DECREASING</constructionHeuristicType>
      </constructionHeuristic>
    </solver>
  </inheritedSolverBenchmark>

<#list [5, 7, 11, 13] as entityTabuSize>
<#list [500, 1000, 2000] as acceptedCountLimit>
  <solverBenchmark>
    <name>entityTabuSize ${entityTabuSize} acceptedCountLimit ${acceptedCountLimit}</name>
    <solver>
      <localSearch>
        <unionMoveSelector>
          <changeMoveSelector/>
          <swapMoveSelector/>
        </unionMoveSelector>
        <acceptor>
          <entityTabuSize>${entityTabuSize}</entityTabuSize>
        </acceptor>
        <forager>
          <acceptedCountLimit>${acceptedCountLimit}</acceptedCountLimit>
        </forager>
      </localSearch>
    </solver>
  </solverBenchmark>
</#list>
</#list>
</plannerBenchmark>
