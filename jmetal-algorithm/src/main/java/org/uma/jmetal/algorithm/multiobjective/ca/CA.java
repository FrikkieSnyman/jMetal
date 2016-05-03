//  CulturalAlgoritm.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

//package jmetal.metaheuristics.CulturalAlgorithm;

package algorithm;


import org.uma.jmetal.algorithm.impl.AbstractEvolutionStrategy;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.SolutionUtils;
import util.DominanceRandomOs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of the Cultural Algorithm.
 */

public class CulturalAlgorithm extends AbstractEvolutionStrategy<DoubleSolution, List<DoubleSolution>> {


    private final Comparator<DoubleSolution> comparator = new DominanceRandomOs(3);

    private int maxEvaluations;
    private int evaluations = 0;

    private BeliefSpace belief;
    private final double acceptancePercent = 0.10;
    private int acceptanceCount;

    private static Random random = new Random();

    private final SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator = new BinaryTournamentSelection<>(comparator);

    public CulturalAlgorithm(int maxEvaluations, int maxPopulationSize, DoubleProblem problem) {
        super(problem);
        this.maxEvaluations = maxEvaluations;

        setMaxPopulationSize(maxPopulationSize);
        acceptanceCount = (int)(maxPopulationSize * acceptancePercent);

    }

    private List<DoubleSolution> variate(List<DoubleSolution> population) {
        Problem problem = getProblem();
        NormativeDimension norm = belief.normativeDimensions[evaluations];
        int numberOfVariables = problem.getNumberOfVariables();
        double[] maxsteps = IntStream.range(0, numberOfVariables)
                .mapToDouble(j -> norm.upper[j]- norm.lower[j]).toArray();

        DoubleSolution situationalSolution = belief.situational[evaluations];
        double[] sitVariables = IntStream.range(0, numberOfVariables).
                mapToDouble(situationalSolution::getVariableValue).toArray();

        Function<DoubleSolution, DoubleSolution> mutate = (parent) -> {
            DoubleSolution offspring = (DoubleSolution) parent.copy();
            for (int j=0; j<numberOfVariables; j++) {
                Double xij = offspring.getVariableValue(j);
                double yj = sitVariables[j];
                if (xij < yj) {
                    xij += Math.abs(maxsteps[j] * random.nextGaussian());
                } else if (xij > yj) {
                    xij -= Math.abs(maxsteps[j] * random.nextGaussian());
                } else {
                    xij += maxsteps[j] * random.nextGaussian();
                }
                offspring.setVariableValue(j, xij);
            }
            return offspring;
        };

        return population.stream().map(mutate).collect(Collectors.toList());
    }

    private List<DoubleSolution> select(List<DoubleSolution> population, int n) {
        List<DoubleSolution> accepted = new ArrayList<>(n);
        for (int i = 0; i< n; ++i) {
            accepted.add(selectionOperator.execute(population));
        }
        return accepted;
    }

    private void adjustBelief(List<DoubleSolution> accepted) {
        DoubleSolution best = SolutionListUtils.findBestSolution(accepted, comparator);
        if (evaluations > 0) {
            belief.addSituational(evaluations,SolutionUtils.getBestSolution(best, belief.situational[evaluations-1], comparator));

            NormativeDimension norm = belief.normativeDimensions[evaluations];
            for (DoubleSolution mem : accepted) {
                normaticeUpdate(mem, norm);
            }
        }
    }

    private void normaticeUpdate(DoubleSolution member, NormativeDimension norm) {
        for (int i=0; i<member.getNumberOfVariables(); ++i) {
            if(member.getLowerBound(i) <= norm.lower[i]
                    || comparator.compare(member, norm.lowerFitness[i]) < 0) {
                setLower(member, norm, i);
            }

            if(member.getUpperBound(i) >= norm.upper[i]
                    || comparator.compare(member, norm.upperFitness[i]) < 0) {
                setUpper(member, norm, i);
            }
        }
    }

    private void setLower(DoubleSolution best, NormativeDimension norm, int i) {
        norm.lower[i] = best.getLowerBound(i);
        norm.lowerFitness[i] = best;
    }

    private void setUpper(DoubleSolution best, NormativeDimension norm, int i) {
        norm.upper[i] = best.getLowerBound(i);
        norm.upperFitness[i] = best;
    }

    protected BeliefSpace createInitialBelief() {
        BeliefSpace belief = new BeliefSpace();
        DoubleSolution best = SolutionListUtils.findBestSolution(getPopulation(), comparator);

        belief.addSituational(0, best);
        NormativeDimension norm = belief.normativeDimensions[0];
        for (int i=0; i<best.getNumberOfVariables(); ++i) {
            setLower(best, norm, i);
            setUpper(best, norm, i);
        }
        return belief;
    }


    @Override
    protected void initProgress() {
        evaluations = 0;
        belief = createInitialBelief();
    }

    @Override
    protected void updateProgress() {
        evaluations++;
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return evaluations >= maxEvaluations;
    }

    protected List<DoubleSolution> createInitialPopulation() {
        Problem<DoubleSolution> problem = getProblem();
        int maxPopulationSize = getMaxPopulationSize();
        List<DoubleSolution> solutionList = new ArrayList<>(maxPopulationSize);
        for (int i = 0; i < maxPopulationSize; ++i) {
            solutionList.add(problem.createSolution());
        }
        return solutionList;
    }


    protected List<DoubleSolution> evaluatePopulation(List<DoubleSolution> population) {
        Problem<DoubleSolution> p = getProblem();
        population.forEach(p::evaluate);
        return population;
    }

    @Override
    protected List<DoubleSolution> selection(List<DoubleSolution> population) {
        return population;
    }

    @Override
    protected List<DoubleSolution> reproduction(List<DoubleSolution> population) {
        List<DoubleSolution> accepted = select(population, this.acceptanceCount);
        adjustBelief(accepted);
        return variate(population);
    }

    @Override
    protected List<DoubleSolution> replacement(List<DoubleSolution> population, List<DoubleSolution> offspringPopulation) {
        return select(offspringPopulation, getMaxPopulationSize());
    }

    @Override
    public void run() {
        super.run();
        this.belief = null;
        this.random = null;
        System.gc();
    }

    @Override
    public List<DoubleSolution> getResult() {
        return getPopulation();
    }

    @Override
    public String getName() {
        return "CA";
    }

    @Override
    public String getDescription() {
        return "Cultural Algorithm";
    }

    private class BeliefSpace {

        //Keep track of the best solution of each generation
        public DoubleSolution[] situational;
        //Keep track of Normative values of each generation
        public NormativeDimension[] normativeDimensions;

        public BeliefSpace() {
            Problem<DoubleSolution> problem = getProblem();
            this.situational = new DoubleSolution[maxEvaluations];
            this.normativeDimensions = new NormativeDimension[maxEvaluations];
            int numberOfVariables = problem.getNumberOfVariables();
            for (int i = 0; i < normativeDimensions.length; i++) {
                normativeDimensions[i] = new NormativeDimension(numberOfVariables);
            }

        }

        public void addSituational(int i, DoubleSolution value) {
            situational[i] = value;
        }
    }

    public class NormativeDimension{
        public double[] upper;
        public double[] lower;
        public DoubleSolution[] upperFitness;
        public DoubleSolution[] lowerFitness;
        public NormativeDimension(int numberOfVariables) {
            this.upper = new double[numberOfVariables];
            this.lower = new double[numberOfVariables];
            this.upperFitness = new DoubleSolution[numberOfVariables];
            this.lowerFitness = new DoubleSolution[numberOfVariables];
        }
    }
}
