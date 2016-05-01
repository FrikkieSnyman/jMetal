package org.uma.jmetal.algorithm.impl;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frikkiesnyman on 2016/04/29.
 */
public abstract class AbstractCulturalAlgorithm <S extends Solution<?>, R>  extends AbstractGeneticAlgorithm<S, R> {
    private List<S> population;
    private BeliefSpace beliefSpace;

    public AbstractCulturalAlgorithm(Problem<S> problem) {
        super(problem);
    }


    @Override public void run() {
        population = createInitialPopulation();
        beliefSpace = createInitialBeliefSpace();

        initProgress();
        while (!isStoppingConditionReached()) {
            population = evaluatePopulation(population);
            beliefSpace = adjust(beliefSpace);
            population = variate(population);
            updateProgress();
        }
    }

    private List<S> variate(List<S> population) {
        return population;
    }

    private BeliefSpace adjust(BeliefSpace beliefSpace) {
        return beliefSpace;
    }

    private List<S> accept(List<S> population) {
        ArrayList<S> influential = new ArrayList<>();

        return influential;
    }

    private List<S> influence(List<S> population, List<S> influential) {
        return population;
    }

    private BeliefSpace createInitialBeliefSpace() {

        return null;
    }

    private class BeliefSpace {

        List<S> situationalKnowledge;
        Normative normativeKnowledge;

        public List<S> getSituationalKnowledge() {
            return situationalKnowledge;
        }

        public void setSituationalKnowledge(List<S> situationalKnowledge) {
            this.situationalKnowledge = situationalKnowledge;
        }

        public Normative getNormativeKnowledge() {
            return normativeKnowledge;
        }

        public void setNormativeKnowledge(Normative normativeKnowledge) {
            this.normativeKnowledge = normativeKnowledge;
        }

        public BeliefSpace() {
            situationalKnowledge = new ArrayList<>();
            normativeKnowledge = new Normative();
        }

        private class Normative {
            S lowerBound;
            S upperBound;

            public Normative() {}

            public Normative(S lBound, S uBound) {
                lowerBound = lBound;
                upperBound = uBound;
                getProblem().evaluate(lowerBound);
                getProblem().evaluate(upperBound);
            }

            public S getLowerBound() {
                return lowerBound;
            }

            public void setLowerBound(S lowerBound) {
                this.lowerBound = lowerBound;
                getProblem().evaluate(lowerBound);
            }

            public  S getUpperBound() {
                return upperBound;
            }

            public void setUpperBound(S upperBound) {
                this.upperBound = upperBound;
                getProblem().evaluate(upperBound);
            }
        }
    }

}
