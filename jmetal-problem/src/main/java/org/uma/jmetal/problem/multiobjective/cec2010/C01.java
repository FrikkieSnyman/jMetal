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

    }

    @Override
    public void evaluate(DoubleSolution solution) {

    }
}
