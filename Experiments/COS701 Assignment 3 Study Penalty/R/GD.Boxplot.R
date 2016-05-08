postscript("GD.Boxplot.eps", horizontal=FALSE, onefile=FALSE, height=8, width=12, pointsize=10)
resultDirectory<-"../data"
qIndicator <- function(indicator, problem)
{
fileNSGAII<-paste(resultDirectory, "NSGAII", sep="/")
fileNSGAII<-paste(fileNSGAII, problem, sep="/")
fileNSGAII<-paste(fileNSGAII, indicator, sep="/")
NSGAII<-scan(fileNSGAII)

algs<-c("NSGAII")
boxplot(NSGAII,names=algs, notch = FALSE)
titulo <-paste(indicator, problem, sep=":")
title(main=titulo)
}
par(mfrow=c(3,3))
indicator<-"GD"
qIndicator(indicator, "Binh2Penalty")
qIndicator(indicator, "Osyczka2Penalty")
qIndicator(indicator, "SrinivasPenalty")
qIndicator(indicator, "TanakaPenalty")
qIndicator(indicator, "TwoBarTrussPenalty")
qIndicator(indicator, "WeldedBeamPenalty")
qIndicator(indicator, "Binh2")
qIndicator(indicator, "Osyczka2")
qIndicator(indicator, "Srinivas")
qIndicator(indicator, "Tanaka")
qIndicator(indicator, "TwoBarTruss")
qIndicator(indicator, "WeldedBeam")
