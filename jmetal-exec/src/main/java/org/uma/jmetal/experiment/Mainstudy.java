package org.uma.jmetal.experiment;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.ca.CABuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.*;
import org.uma.jmetal.problem.multiobjective.penaltyFunctions.*;
import org.uma.jmetal.qualityindicator.impl.*;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.TaggedAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder.Variant.MOEAD;

/**
 * Created by chris on 5/7/16.
 */
public class Mainstudy {
    private static final int INDEPENDENT_RUNS = 30;

    public static void main(String[] args) throws IOException {
//        if (args.length != 1) {
//            throw new JMetalException("Missing argument: experiment base directory");
//        }
        String experimentBaseDirectory = "./Experiments" ;
        List<Problem<DoubleSolution>> problemList = null;
        try {
            problemList = Arrays.<Problem<DoubleSolution>>asList(new Binh2Penalty(), new Osyczka2Penalty(), new SrinivasPenalty(), new TanakaPenalty(), new TwoBarTrussPenalty(), new WeldedBeamPenalty(),
                    new Binh2(), new Osyczka2(), new Srinivas(), new Tanaka(), new TwoBarTruss(), new WeldedBeam());
        }catch(Exception ex)
        {

        }

        List<TaggedAlgorithm<List<DoubleSolution>>> algorithmList = configureAlgorithmList(problemList, INDEPENDENT_RUNS) ;

        List<String> referenceFrontFileNames = asList("Binh2.pf", "Osyczka2.pf", "Shrinivas.pf", "Tanaka.pf", "TwoBarTruss.pf", "WeldedBeam.pf",
                "Binh2Penalty.pf","Osyczka2Penalty.pf","SrinivasPenalty.pf","TanakaPenalty.pf","TwoBarTrussPenalty.pf","WeldedBeamPenalty.pf");

        Experiment<DoubleSolution, List<DoubleSolution>> experiment =
                new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>("COS701 Assignment 3 Study Penalty")
                        .setAlgorithmList(algorithmList)
                        .setProblemList(problemList)
                        .setExperimentBaseDirectory(experimentBaseDirectory)
                        .setOutputParetoFrontFileName("FUN")
                        .setOutputParetoSetFileName("VAR")
                        .setReferenceFrontDirectory("pareto_fronts")
                        .setReferenceFrontFileNames(referenceFrontFileNames)
                        .setIndicatorList(asList(
                                new Epsilon<DoubleSolution>(), new Spread<DoubleSolution>(), new GenerationalDistance<DoubleSolution>(),
                                new PISAHypervolume<DoubleSolution>(),
                                new InvertedGenerationalDistance<DoubleSolution>(), new InvertedGenerationalDistancePlus<DoubleSolution>()))
                        .setIndependentRuns(INDEPENDENT_RUNS)
                        .setNumberOfCores(4)
                        .build();

        new ExecuteAlgorithms<>(experiment).run();

        new GenerateReferenceParetoFront(experiment).run();
        new ComputeQualityIndicators<>(experiment).run() ;
        new GenerateLatexTablesWithStatistics(experiment).run() ;
        new GenerateWilcoxonTestTablesWithR<>(experiment).run() ;
        new GenerateFriedmanTestTables<>(experiment).run();
        new GenerateBoxplotsWithR<>(experiment).setRows(3).setColumns(3).run() ;
    }

    /**
     * The algorithm list is composed of pairs {@link Algorithm} + {@link Problem} which form part of a
     * {@link TaggedAlgorithm}, which is a decorator for class {@link Algorithm}. The {@link TaggedAlgorithm}
     * has an optional tag component, that can be set as it is shown in this example, where four variants of a
     * same algorithm are defined.
     *
     * @param problemList
     * @return
     */
    static List<TaggedAlgorithm<List<DoubleSolution>>> configureAlgorithmList(
            List<Problem<DoubleSolution>> problemList, int independentRuns) {
        List<TaggedAlgorithm<List<DoubleSolution>>> algorithms = new ArrayList<>() ;

        for (int run = 0; run < independentRuns; run++) {
            // Paramaters are set from

            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i), new SBXCrossover(0.8, 5),
                        new PolynomialMutation(1.0 / problemList.get(i).getNumberOfVariables(), 10.0))
                        .setMaxEvaluations(5000)
                        .setPopulationSize(100)
                        .build();
                algorithms.add(new TaggedAlgorithm<List<DoubleSolution>>(algorithm, "NSGAII", problemList.get(i), run));
            }


            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new MOEADBuilder(problemList.get(i), MOEADBuilder.Variant.ConstraintMOEAD)
                        .setCrossover(new DifferentialEvolutionCrossover(1.0, 0.5, "rand/1/bin"))
                        .setMutation(new PolynomialMutation(1.0 / problemList.get(i).getNumberOfVariables(), 10.0))
                        .setMaxEvaluations(150000)
                        .setPopulationSize(300)
                        .setResultPopulationSize(300)
                        .setNeighborhoodSelectionProbability(0.9)
                        .setMaximumNumberOfReplacedSolutions(2)
                        .setNeighborSize(20)
                        .setFunctionType(AbstractMOEAD.FunctionType.TCHE)
                        .setDataDirectory("MOEAD_Weights")
                        .build() ;
                algorithms.add(new TaggedAlgorithm<List<DoubleSolution>>(algorithm, "MOEAD", problemList.get(i), run));
            }


            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new PAESBuilder<>(problemList.get(i))
                        .setMaxEvaluations(5000)
                        .setBiSections(3)
                        .setMutationOperator(new PolynomialMutation())
                        .build();
                algorithms.add(new TaggedAlgorithm<List<DoubleSolution>>(algorithm, "PAES", problemList.get(i), run));
            }

            /*for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new CABuilder<>((DoubleProblem) problemList.get(i), 5000, 20)
                        .setMutationOperator(new PolynomialMutation())
                        .build();
                algorithms.add(new TaggedAlgorithm<List<DoubleSolution>>(algorithm, "CA", problemList.get(i), run));
            }*/


        }
        return algorithms ;
    }


}