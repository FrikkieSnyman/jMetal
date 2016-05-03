write("", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex",append=FALSE)
resultDirectory<-"./Experiments/NSGAIIStudy/data"
latexHeader <- function() {
  write("\\documentclass{article}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\title{StandardStudy}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\usepackage{amssymb}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\author{A.J.Nebro}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\begin{document}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\maketitle", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\section{Tables}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
}

latexTableHeader <- function(problem, tabularString, latexTableFirstLine) {
  write("\\begin{table}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\caption{", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write(problem, "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write(".IGD+.}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)

  write("\\label{Table:", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write(problem, "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write(".IGD+.}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)

  write("\\centering", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\begin{scriptsize}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\begin{tabular}{", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write(tabularString, "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write(latexTableFirstLine, "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\hline ", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
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
    write("-- ", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  }
  else if (i < j) {
    if (is.finite(wilcox.test(data1, data2)$p.value) & wilcox.test(data1, data2)$p.value <= 0.05) {
      if (median(data1) <= median(data2)) {
        write("$\\blacktriangle$", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
      }
      else {
        write("$\\triangledown$", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE) 
      }
    }
    else {
      write("--", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE) 
    }
  }
  else {
    write(" ", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  }
}

latexTableTail <- function() { 
  write("\\hline", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\end{tabular}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\end{scriptsize}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
  write("\\end{table}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
}

latexTail <- function() { 
  write("\\end{document}", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
}

### START OF SCRIPT 
# Constants
problemList <-c("Binh2") 
algorithmList <-c("NSGAIIa", "NSGAIIb", "NSGAIIc", "NSGAIId") 
tabularString <-c("lccc") 
latexTableFirstLine <-c("\\hline  & NSGAIIb & NSGAIIc & NSGAIId\\\\ ") 
indicator<-"IGD+"

 # Step 1.  Writes the latex header
latexHeader()
tabularString <-c("| l | p{0.15cm } | p{0.15cm } | p{0.15cm } | ") 

latexTableFirstLine <-c("\\hline \\multicolumn{1}{|c|}{} & \\multicolumn{1}{c|}{NSGAIIb} & \\multicolumn{1}{c|}{NSGAIIc} & \\multicolumn{1}{c|}{NSGAIId} \\\\") 

# Step 3. Problem loop 
latexTableHeader("Binh2 ", tabularString, latexTableFirstLine)

indx = 0
for (i in algorithmList) {
  if (i != "NSGAIId") {
    write(i , "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
    write(" & ", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)

    jndx = 0
    for (j in algorithmList) {
      for (problem in problemList) {
        if (jndx != 0) {
          if (i != j) {
            printTableLine(indicator, i, j, indx, jndx, problem)
          }
          else {
            write("  ", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
          } 
          if (problem == "Binh2") {
            if (j == "NSGAIId") {
              write(" \\\\ ", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
            } 
            else {
              write(" & ", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
            }
          }
     else {
    write("&", "./Experiments/NSGAIIStudy/R/IGD+.Wilcoxon.tex", append=TRUE)
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

