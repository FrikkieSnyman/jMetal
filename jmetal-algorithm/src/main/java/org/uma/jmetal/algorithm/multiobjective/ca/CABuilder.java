//package org.uma.jmetal.algorithm.multiobjective.ca;
//
//import org.uma.jmetal.problem.DoubleProblem;
//import org.uma.jmetal.util.AlgorithmBuilder;
//
///**
// * @author renet
// *         on 2016/04/29.
// */
//public class CABuilder implements AlgorithmBuilder<CA> {
//    private DoubleProblem problem;
//    private int maxEvaluations;
//    private int maxPopulationSize;
//
//    public CABuilder(DoubleProblem problem, int maxEvaluations, int maxPopulationSize) {
//        this.problem = problem;
//        this.maxEvaluations = maxEvaluations;
//        this.maxPopulationSize = maxPopulationSize;
//    }
//
//
//    public CA build() {
//        return new CA(maxEvaluations, maxPopulationSize, problem);
//    }
//
//
//}

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

package org.uma.jmetal.algorithm.multiobjective.ca;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class CABuilder<S extends Solution<?>>  implements AlgorithmBuilder<CA> {
    private DoubleProblem problem;

    private int maxEvaluations;
    private int maxPopSize;
    private MutationOperator<S> mutationOperator;

    public CABuilder(DoubleProblem problem, int maxEvaluations, int maxPopulationSize) {
        this.maxEvaluations = maxEvaluations;
        this.maxPopSize = maxPopulationSize;
        this.problem = problem;
    }

    public CABuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;

        return this;
    }


    public CABuilder<S> setMutationOperator(MutationOperator<S> mutation) {
        mutationOperator = mutation;

        return this;
    }

    public CA build() {
//        return new PAES<S>(problem, archiveSize, maxEvaluations, biSections, mutationOperator);
        return new CA(maxEvaluations, maxPopSize, problem);
    }


    /*
     * Getters
     */
    public DoubleProblem getProblem() {
        return problem;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }
}
