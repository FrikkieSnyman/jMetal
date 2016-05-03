package algorithm;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.util.AlgorithmBuilder;

/**
 * @author renet
 *         on 2016/04/29.
 */
public class CulturalBuilder implements AlgorithmBuilder<CulturalAlgorithm> {
    private DoubleProblem problem;
    private int maxEvaluations;
    private int maxPopulationSize;

    public CulturalBuilder(DoubleProblem problem, int maxEvaluations, int maxPopulationSize) {
        this.problem = problem;
        this.maxEvaluations = maxEvaluations;
        this.maxPopulationSize = maxPopulationSize;
    }


    public CulturalAlgorithm build() {
        return new CulturalAlgorithm(maxEvaluations, maxPopulationSize, problem);
    }


}
