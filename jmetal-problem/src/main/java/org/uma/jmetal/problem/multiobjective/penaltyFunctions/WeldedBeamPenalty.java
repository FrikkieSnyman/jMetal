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
public class WeldedBeamPenalty extends AbstractDoubleProblem implements ConstrainedProblem<DoubleSolution> {
    public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree ;
    public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints ;
    private double alpha;
    private double lamda;
    private double lamdaM;

    public WeldedBeamPenalty()  {
        setNumberOfVariables(4);
        setNumberOfObjectives(2);
        setNumberOfConstraints(4);
        setName("WeldedBeamPenalty");
        alpha = 2.0;
        lamda = 0.1;
        lamdaM = 1/getNumberOfConstraints();

        List<Double> lowerLimit = Arrays.asList(0.125, 0.125, 0.1, 0.1);
        List<Double> upperLimit = Arrays.asList(5.0, 5.0, 10.0, 10.0);

        setLowerLimit(lowerLimit);
        setUpperLimit(upperLimit);

        overallConstraintViolationDegree = new OverallConstraintViolation<DoubleSolution>() ;
        numberOfViolatedConstraints = new NumberOfViolatedConstraints<DoubleSolution>() ;
    }

    @Override
    public void evaluate(DoubleSolution solution) {
        double h, b, l, t;
        h = solution.getVariableValue(0);
        b = solution.getVariableValue(1);
        l = solution.getVariableValue(2);
        t = solution.getVariableValue(3);

//        double tau2 = (6000 * (14 + 0.5 * l) * Math.sqrt(0.25 * (Math.pow(l, 2) + Math.pow((h + t), 2)))) /
//                (2 * Math.sqrt(2) * h * l * ((Math.pow(l, 2) / 12) + 0.25 * Math.pow((h + t), 2)));
//        double tau1 = 6000 / (Math.sqrt(2) * h * l);
//        double tau = Math.sqrt(Math.pow(tau1, 2) + Math.pow(tau2, 2) + (l * tau1 * tau2) / Math.sqrt(0.25 * (Math.pow(l, 2) + Math.pow((h + t), 2))));
//        double sigma = 504000 / (Math.pow(t, 2) * b);
//        double pc = 64746.022 * (1 - 0.0282346 * t) * t * Math.pow(b, 3);
        double[] constraint = new double[this.getNumberOfConstraints()];

        double tau2 = (6000 * (14 + 0.5 * l) * Math.sqrt(0.25 * (Math.pow(l, 2) + Math.pow((h + t), 2)))) /
                (2 * Math.sqrt(2) * h * l * ((Math.pow(l, 2) / 12) + 0.25 * Math.pow((h + t), 2)));
        double tau1 = 6000 / (Math.sqrt(2) * h * l);
        double tau = Math.sqrt(Math.pow(tau1, 2) + Math.pow(tau2, 2) + (l * tau1 * tau2) / Math.sqrt(0.25 * (Math.pow(l, 2) + Math.pow((h + t), 2))));
        double sigma = 504000 / (Math.pow(t, 2) * b);
        double pc = 64746.022 * (1 - 0.0282346 * t) * t * Math.pow(b, 3);

        constraint[0] = 13600 - tau;
        constraint[1] = 30000 - sigma;
        constraint[2] = b - h;
        constraint[3] = pc - 6000;

        double c1 = Math.max(0, Math.pow(constraint[0], alpha));
        double c2 = Math.max(0, Math.pow(constraint[1], alpha));
        double c3 = Math.max(0, Math.pow(constraint[2], alpha));
        double c4 = Math.max(0, Math.pow(constraint[3], alpha));

        double penalty = lamdaM*c1 + lamdaM*c2 + lamdaM*c3 + lamdaM*c4;

        double f1 = 1.10471 * Math.pow(h, 2) * l + 0.04811 * t * b * (14 + l) + lamda*penalty;
        double f2 = (2.1952 / (Math.pow(t, 3) * b)) + lamda*penalty ;

        solution.setObjective(0, f1);
        solution.setObjective(1, f2);
    }

    @Override
    public void evaluateConstraints(DoubleSolution solution) {
        double[] constraint = new double[this.getNumberOfConstraints()];

        double h, b, l, t;
        h = solution.getVariableValue(0);
        b = solution.getVariableValue(1);
        l = solution.getVariableValue(2);
        t = solution.getVariableValue(3);

        double tau2 = (6000 * (14 + 0.5 * l) * Math.sqrt(0.25 * (Math.pow(l, 2) + Math.pow((h + t), 2)))) /
                (2 * Math.sqrt(2) * h * l * ((Math.pow(l, 2) / 12) + 0.25 * Math.pow((h + t), 2)));
        double tau1 = 6000 / (Math.sqrt(2) * h * l);
        double tau = Math.sqrt(Math.pow(tau1, 2) + Math.pow(tau2, 2) + (l * tau1 * tau2) / Math.sqrt(0.25 * (Math.pow(l, 2) + Math.pow((h + t), 2))));
        double sigma = 504000 / (Math.pow(t, 2) * b);
        double pc = 64746.022 * (1 - 0.0282346 * t) * t * Math.pow(b, 3);

        constraint[0] = 13600 - tau;
        constraint[1] = 30000 - sigma;
        constraint[2] = b - h;
        constraint[3] = pc - 6000;

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
