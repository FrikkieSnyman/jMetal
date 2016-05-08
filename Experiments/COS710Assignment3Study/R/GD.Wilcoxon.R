write("", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex",append=FALSE)
resultDirectory<-"./Experiments/COS710Assignment3Study/data"
latexHeader <- function() {
  write("\\documentclass{article}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\title{StandardStudy}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\usepackage{amssymb}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\author{A.J.Nebro}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\begin{document}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\maketitle", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\section{Tables}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
}

latexTableHeader <- function(problem, tabularString, latexTableFirstLine) {
  write("\\begin{table}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\caption{", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write(problem, "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write(".GD.}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)

  write("\\label{Table:", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write(problem, "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write(".GD.}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)

  write("\\centering", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\begin{scriptsize}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\begin{tabular}{", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write(tabularString, "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write(latexTableFirstLine, "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\hline ", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
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
    write("-- ", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  }
  else if (i < j) {
    if (is.finite(wilcox.test(data1, data2)$p.value) & wilcox.test(data1, data2)$p.value <= 0.05) {
      if (median(data1) <= median(data2)) {
        write("$\\blacktriangle$", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
      }
      else {
        write("$\\triangledown$", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE) 
      }
    }
    else {
      write("--", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE) 
    }
  }
  else {
    write(" ", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  }
}

latexTableTail <- function() { 
  write("\\hline", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\end{tabular}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\end{scriptsize}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
  write("\\end{table}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
}

latexTail <- function() { 
  write("\\end{document}", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
}

### START OF SCRIPT 
# Constants
problemList <-c("Binh2", "Srinivas", "Osyczka2", "Tanaka", "TwoBarTruss", "WeldedBeam", "Binh2Penalty", "SrinivasPenalty", "Osyczka2Penalty", "TanakaPenalty", "TwoBarTrussPenalty", "WeldedBeamPenalty") 
algorithmList <-c("SteadyStateNSGAII", "MOEAD", "PAES", "CA") 
tabularString <-c("lccc") 
latexTableFirstLine <-c("\\hline  & MOEAD & PAES & CA\\\\ ") 
indicator<-"GD"

 # Step 1.  Writes the latex header
latexHeader()
tabularString <-c("| l | p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm } | p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm } | p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm }p{0.15cm } | ") 

latexTableFirstLine <-c("\\hline \\multicolumn{1}{|c|}{} & \\multicolumn{12}{c|}{MOEAD} & \\multicolumn{12}{c|}{PAES} & \\multicolumn{12}{c|}{CA} \\\\") 

# Step 3. Problem loop 
latexTableHeader("Binh2 Srinivas Osyczka2 Tanaka TwoBarTruss WeldedBeam Binh2Penalty SrinivasPenalty Osyczka2Penalty TanakaPenalty TwoBarTrussPenalty WeldedBeamPenalty ", tabularString, latexTableFirstLine)

indx = 0
for (i in algorithmList) {
  if (i != "CA") {
    write(i , "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
    write(" & ", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)

    jndx = 0
    for (j in algorithmList) {
      for (problem in problemList) {
        if (jndx != 0) {
          if (i != j) {
            printTableLine(indicator, i, j, indx, jndx, problem)
          }
          else {
            write("  ", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
          } 
          if (problem == "WeldedBeamPenalty") {
            if (j == "CA") {
              write(" \\\\ ", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
            } 
            else {
              write(" & ", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
            }
          }
     else {
    write("&", "./Experiments/COS710Assignment3Study/R/GD.Wilcoxon.tex", append=TRUE)
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

