write("", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex",append=FALSE)
resultDirectory<-"./Experiments/COS701 Assignment 3 Study Penalty/data"
latexHeader <- function() {
  write("\\documentclass{article}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\title{StandardStudy}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\usepackage{amssymb}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\author{A.J.Nebro}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\begin{document}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\maketitle", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\section{Tables}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
}

latexTableHeader <- function(problem, tabularString, latexTableFirstLine) {
  write("\\begin{table}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\caption{", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write(problem, "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write(".GD.}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)

  write("\\label{Table:", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write(problem, "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write(".GD.}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)

  write("\\centering", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\begin{scriptsize}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\begin{tabular}{", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write(tabularString, "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write(latexTableFirstLine, "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\hline ", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
}

printTableLine <- function(indicator, algorithm1, algorithm2, i, j, problem) { 
  file1<-paste(resultDirectory, algorithm1, sep="/")
  file1<-paste(file1, problem, sep="/")
  file1<-paste(file1, indicator, sep="/")
  data1<-scan(file1)
  file2<-paste(resultDirectory, algorithm2, sep="/")
  file2<-paste(file2, problem, sep="/")
  file2<-paste(file2, indicator, sep="/")
  data2<-scan(file2)
  if (i == j) {
    write("-- ", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  }
  else if (i < j) {
    if (is.finite(wilcox.test(data1, data2)$p.value) & wilcox.test(data1, data2)$p.value <= 0.05) {
      if (median(data1) <= median(data2)) {
        write("$\\blacktriangle$", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
      }
      else {
        write("$\\triangledown$", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE) 
      }
    }
    else {
      write("--", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE) 
    }
  }
  else {
    write(" ", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  }
}

latexTableTail <- function() { 
  write("\\hline", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\end{tabular}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\end{scriptsize}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\end{table}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
}

latexTail <- function() { 
  write("\\end{document}", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
}

### START OF SCRIPT 
# Constants
problemList <-c("Binh2Penalty", "Osyczka2Penalty", "SrinivasPenalty", "TanakaPenalty", "TwoBarTrussPenalty", "WeldedBeamPenalty", "Binh2", "Osyczka2", "Srinivas", "Tanaka", "TwoBarTruss", "WeldedBeam") 
algorithmList <-c("NSGAII") 
tabularString <-c("l") 
latexTableFirstLine <-c("\\hline \\\\ ") 
indicator<-"GD"

 # Step 1.  Writes the latex header
latexHeader()
tabularString <-c("| l | ") 

latexTableFirstLine <-c("\\hline \\multicolumn{1}{|c|}{} \\\\") 

# Step 3. Problem loop 
latexTableHeader("Binh2Penalty Osyczka2Penalty SrinivasPenalty TanakaPenalty TwoBarTrussPenalty WeldedBeamPenalty Binh2 Osyczka2 Srinivas Tanaka TwoBarTruss WeldedBeam ", tabularString, latexTableFirstLine)

indx = 0
for (i in algorithmList) {
  if (i != "NSGAII") {
    write(i , "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
    write(" & ", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)

    jndx = 0
    for (j in algorithmList) {
      for (problem in problemList) {
        if (jndx != 0) {
          if (i != j) {
            printTableLine(indicator, i, j, indx, jndx, problem)
          }
          else {
            write("  ", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
          } 
          if (problem == "WeldedBeam") {
            if (j == "NSGAII") {
              write(" \\\\ ", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
            } 
            else {
              write(" & ", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
            }
          }
     else {
    write("&", "./Experiments/COS701 Assignment 3 Study Penalty/R/GD.Wilcoxon.tex", append=TRUE)
     }
        }
      }
      jndx = jndx + 1
    }
    indx = indx + 1
  }
} # for algorithm

  latexTableTail()

#Step 3. Writes the end of latex file 
latexTail()

