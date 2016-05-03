package org.uma.jmetal.problem.multiobjective.cec2010;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frikk on 5/2/2016.
 */
public class C01 extends AbstractDoubleProblem implements ConstrainedProblem<DoubleSolution> {

    public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree ;
    public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints ;

    public  C01() {
        this(10);
    }

    public C01(int dimensions) {
        setNumberOfVariables(dimensions);
        setNumberOfObjectives(1);
        setNumberOfConstraints(2);
        setName("C01");

        List<Double> lowerLimit = new ArrayList<>();
        List<Double> upperLimit = new ArrayList<>();

        for (int i = 0; i < dimensions; ++i) {
            lowerLimit.add(0.0);
            upperLimit.add(10.0);
        }

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);

        overallConstraintViolationDegree = new OverallConstraintViolation<>();
        numberOfViolatedConstraints = new NumberOfViolatedConstraints<>();

    }

    @Override
    public void evaluateConstraints(DoubleSolution solution) {
        double[] constraint = new double[getNumberOfConstraints()];
        double[] x = new double[getNumberOfVariables()];
        for (int i = 0; i < getNumberOfVariables(); i++) {
            x[i] = solution.getVariableValue(i) ;
        }
        double a = 1.0;
        double b = 0.0;
        for (int i = 0; i < getNumberOfVariables(); ++i) {
            a *= x[i];
            b += x[i];
        }

        constraint[0] = -(0.75 - a);
        constraint[1] = -(b - 7.5 * getNumberOfVariables());

        double overallConstraintViolation = 0.0;
        int violatedConstraints = 0;

        for (int i = 0; i < getNumberOfConstraints(); i++) {
            if (constraint[i] < 0.0){
                overallConstraintViolation+=constraint[i];
                violatedConstraints++;
            }
        }


        overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
        numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);
    }

    @Override
    public void evaluate(DoubleSolution solution) {
        double[] fx = new double[getNumberOfObjectives()];
        double[] x = new double[getNumberOfVariables()];
        for (int i = 0; i < getNumberOfVariables(); i++) {
            x[i] = solution.getVariableValue(i) ;
        }

        double a = 0.0;
        double b = 1.0;
        double c = 0.0;
        for (int i = 0; i < getNumberOfVariables(); ++i) {
            a += Math.pow(Math.cos(x[i]), 4);
            b *= Math.pow(Math.cos(x[i]), 2);
            c += i * Math.pow(x[i],2);
        }

        fx[0] = -Math.abs((a - 2*b) / Math.sqrt(c));

        solution.setObjective(0, fx[0]);

    }
}
