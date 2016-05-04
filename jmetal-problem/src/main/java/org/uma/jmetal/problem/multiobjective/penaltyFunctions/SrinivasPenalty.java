package org.uma.jmetal.problem.multiobjective.penaltyFunctions;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2016/05/04.
 */
public class SrinivasPenalty extends AbstractDoubleProblem implements ConstrainedProblem<DoubleSolution> {

    public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree ;
    public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints ;
    private double alpha;
    private double lamda;
    private double lamdaM;

    /** Constructor */
    public SrinivasPenalty()  {
        setNumberOfVariables(2);
        setNumberOfObjectives(2);
        setNumberOfConstraints(2);
        setName("SrinivasPenalty");
        alpha = 2.0;
        lamda = 0.1;
        lamdaM = 1/getNumberOfConstraints();

        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

        for (int i = 0; i < getNumberOfVariables(); i++) {
            lowerLimit.add(-20.0);
            upperLimit.add(20.0);
        }

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);

        overallConstraintViolationDegree = new OverallConstraintViolation<DoubleSolution>() ;
        numberOfViolatedConstraints = new NumberOfViolatedConstraints<DoubleSolution>() ;
    }

    /** Evaluate() method */
    @Override
    public void evaluate(DoubleSolution solution)  {
        double[] f = new double[solution.getNumberOfVariables()];

        double x1 = solution.getVariableValue(0);
        double x2 = solution.getVariableValue(1);

        double[] constraint = new double[this.getNumberOfConstraints()];

        constraint[0] = 1.0 - (x1 * x1 + x2 * x2) / 225.0;
        constraint[1] = (3.0 * x2 - x1) / 10.0 - 1.0;

        double c1 = Math.max(0, Math.pow(constraint[0], alpha));
        double c2 = Math.max(0, Math.pow(constraint[1], alpha));
        double penalty = lamdaM*c1 + lamdaM*c2;

        f[0] = 2.0 + (x1 - 2.0) * (x1 - 2.0) + (x2 - 1.0) * (x2 - 1.0) + lamda*penalty;
        f[1] = 9.0 * x1 - (x2 - 1.0) * (x2 - 1.0) +lamda*penalty;

        solution.setObjective(0, f[0]);
        solution.setObjective(1, f[1]);
    }

    /** EvaluateConstraints() method  */
    @Override
    public void evaluateConstraints(DoubleSolution solution)  {
        double[] constraint = new double[this.getNumberOfConstraints()];

        double x1 = solution.getVariableValue(0) ;
        double x2 = solution.getVariableValue(1) ;

        constraint[0] = 1.0 - (x1 * x1 + x2 * x2) / 225.0;
        constraint[1] = (3.0 * x2 - x1) / 10.0 - 1.0;

        double overallConstraintViolation = 0.0;
        int violatedConstraints = 0;
        for (int i = 0; i < getNumberOfConstraints(); i++) {
            if (constraint[i]<0.0){
                overallConstraintViolation+=constraint[i];
                violatedConstraints++;
            }
        }

        overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
        numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);
    }
}
