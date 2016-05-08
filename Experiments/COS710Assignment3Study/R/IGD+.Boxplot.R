postscript("IGD+.Boxplot.eps", horizontal=FALSE, onefile=FALSE, height=8, width=12, pointsize=10)
resultDirectory<-"../data"
qIndicator <- function(indicator, problem)
{
fileSteadyStateNSGAII<-paste(resultDirectory, "SteadyStateNSGAII", sep="/")
fileSteadyStateNSGAII<-paste(fileSteadyStateNSGAII, problem, sep="/")
fileSteadyStateNSGAII<-paste(fileSteadyStateNSGAII, indicator, sep="/")
SteadyStateNSGAII<-scan(fileSteadyStateNSGAII)

fileMOEAD<-paste(resultDirectory, "MOEAD", sep="/")
fileMOEAD<-paste(fileMOEAD, problem, sep="/")
fileMOEAD<-paste(fileMOEAD, indicator, sep="/")
MOEAD<-scan(fileMOEAD)

filePAES<-paste(resultDirectory, "PAES", sep="/")
filePAES<-paste(filePAES, problem, sep="/")
filePAES<-paste(filePAES, indicator, sep="/")
PAES<-scan(filePAES)

algs<-c("SteadyStateNSGAII","MOEAD","PAES")
boxplot(SteadyStateNSGAII,MOEAD,PAES,names=algs, notch = FALSE)
titulo <-paste(indicator, problem, sep=":")
title(main=titulo)
}
par(mfrow=c(3,3))
indicator<-"IGD+"
qIndicator(indicator, "Binh2")
qIndicator(indicator, "Srinivas")
qIndicator(indicator, "Osyczka2")
qIndicator(indicator, "Tanaka")
qIndicator(indicator, "TwoBarTruss")
qIndicator(indicator, "WeldedBeam")
qIndicator(indicator, "Binh2Penalty")
qIndicator(indicator, "SrinivasPenalty")
qIndicator(indicator, "Osyczka2Penalty")
qIndicator(indicator, "TanakaPenalty")
qIndicator(indicator, "TwoBarTrussPenalty")
qIndicator(indicator, "WeldedBeamPenalty")
