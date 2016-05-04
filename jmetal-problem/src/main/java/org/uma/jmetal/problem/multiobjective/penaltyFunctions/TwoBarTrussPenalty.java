package org.uma.jmetal.problem.multiobjective.penaltyFunctions;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Chris on 2016/05/04.
 */
public class TwoBarTrussPenalty extends AbstractDoubleProblem implements ConstrainedProblem<DoubleSolution> {
    public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree ;
    public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints ;
    private double alpha;
    private double lamda;
    private double lamdaM;

    public TwoBarTrussPenalty()  {
        setNumberOfVariables(3);
        setNumberOfObjectives(2);
        setNumberOfConstraints(2);
        setName("TwoBarTrussPenalty");
        alpha = 2.0;
        lamda = 0.1;
        lamdaM = 1/getNumberOfConstraints();

        List<Double> lowerLimit = Arrays.asList(0.0, 0.0, 1.0) ;
        List<Double> upperLimit = Arrays.asList(0.01, 0.01, 3.0) ;

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);

        overallConstraintViolationDegree = new OverallConstraintViolation<DoubleSolution>() ;
        numberOfViolatedConstraints = new NumberOfViolatedConstraints<DoubleSolution>() ;
    }

    @Override
    public void evaluate(DoubleSolution solution) {
        double x1, x2, x3;
        x1 = solution.getVariableValue(0);
        x2 = solution.getVariableValue(1);
        x3 = solution.getVariableValue(2);

        double[] constraint = new double[this.getNumberOfConstraints()];

        double s1 = (20 * Math.sqrt(16 + Math.pow((x3), 2))) / (x1 * x3);
        double s2 = (80 * Math.sqrt(1 + Math.pow((x3), 2))) / (x2 * x3);

        constraint[0] = Math.pow(10, 5) - Math.max(s1, s2);

        double c1 = Math.max(0, Math.pow(constraint[0], alpha));
        double penalty = lamdaM*c1;

        double sigma1 = (20 * Math.sqrt(16 + Math.pow((x3), 2))) / (x1 * x3);
        double sigma2 = (80 * Math.sqrt(1 + Math.pow((x3), 2))) / (x2 * x3);

        double f1 = x1 * Math.sqrt(16 + Math.pow((x3), 2)) + x2 * Math.sqrt(1 + Math.pow((x3), 2)) + lamda*penalty;
        double f2 = Math.max(sigma1, sigma2) + lamda*penalty;

        solution.setObjective(0 ,f1);
        solution.setObjective(1 ,f2);
    }

    @Override
    public void evaluateConstraints(DoubleSolution solution) {
        double[] constraint = new double[this.getNumberOfConstraints()];

        double x1, x2, x3;
        x1 = solution.getVariableValue(0);
        x2 = solution.getVariableValue(1);
        x3 = solution.getVariableValue(2);

        double sigma1 = (20 * Math.sqrt(16 + Math.pow((x3), 2))) / (x1 * x3);
        double sigma2 = (80 * Math.sqrt(1 + Math.pow((x3), 2))) / (x2 * x3);

        constraint[0] = Math.pow(10, 5) - Math.max(sigma1, sigma2);

        double overallConstraintViolation = 0.0;
        int violatedConstraints = 0;
        for (int i = 0; i < this.getNumberOfConstraints(); i++) {
            if (constraint[i] < 0.0) {
                overallConstraintViolation += constraint[i];
                violatedConstraints++;
            }
        }

        overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
        numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);
    }
}
