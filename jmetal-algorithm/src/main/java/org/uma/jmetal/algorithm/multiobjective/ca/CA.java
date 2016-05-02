package org.uma.jmetal.algorithm.multiobjective.ca;

import org.uma.jmetal.algorithm.impl.AbstractCulturalAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.qualityindicator.QualityIndicator;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.distance.Distance;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.solutionattribute.impl.CrowdingDistance;

import java.util.List;

/**
 * Created by frikkiesnyman on 2016/05/01.
 */
public class CA<S extends Solution<?>> extends AbstractCulturalAlgorithm<S, List<S>> {
    protected final int maxEvaluations;
    protected int evaluations;

    protected int requiredEvaluations;

    protected final SolutionListEvaluator<S> evaluator;

    protected MutationOperator<S> mutationOperator;
    protected CrossoverOperator<S> crossoverOperator;
    protected SelectionOperator<List<S>, S> selectionOperator;

    protected CrowdingDistance<S> distance;

    protected JMetalRandom randomGenerator;

    public CA(Problem<S> problem, int maxEvaluations, int populationSize,
              MutationOperator<S> mutationOperator, CrossoverOperator<S> crossoverOperator,
              SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> solutionListEvaluator) {
        super(problem);

        this.maxEvaluations = maxEvaluations;

        setMaxPopulationSize(populationSize);

        this.evaluator = solutionListEvaluator;

        requiredEvaluations = 0;

        this.mutationOperator = mutationOperator;
        this.crossoverOperator = crossoverOperator;
        this.selectionOperator = selectionOperator;
    }

    @Override
    protected void initProgress() {
        evaluations = 0;
    }

    @Override
    protected void updateProgress() {
        evaluations += 1;
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return evaluations >= maxEvaluations;
    }

    @Override
    protected List<S> evaluatePopulation(List<S> population) {
        population = evaluator.evaluate(population, getProblem());

        return population;
    }

    @Override
    protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
        return null;
    }

    @Override
    public List<S> getResult() {
        return null;
    }

    @Override
    public String getName() {
        return "CA";
    }

    @Override
    public String getDescription() {
        return "Cultural Algorithm";
    }
}
