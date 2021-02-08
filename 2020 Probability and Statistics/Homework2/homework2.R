# Enes Garip
# 150116034

###################################

load(file = "Homework_2_50samples.rdata")
rdata<-ex0717
library(HistogramTools)

###################################


############ PART-A ###############

sample_values=rdata$`Sample mean`

RFHistogram<-hist(sample_values,freq = FALSE)
PlotRelativeFrequency(RFHistogram,col=blues9)

print("The distribution is mound-shaped distrubiton")

############ PART-B ###############
calcMeanOfSampleValues=mean(rdata$`Sample mean`)
calcSDOfSampleValues=sd(rdata$`Sample mean`)

print(calcMeanOfSampleValues)
print(calcSDOfSampleValues)

############ PART-C ###############

theoreticalMean=4.4
theoreticalSD=2.15/sqrt(10)

differenceOfMeans=theoreticalMean-calcMeanOfSampleValues
differenceOfSD=theoreticalSD-calcSDOfSampleValues

print(differenceOfMeans)
print(differenceOfSD)

sprintf("The difference of means is %f and the value is very small so the theoretical mean is very close to the population mean",differenceOfMeans)
sprintf("The difference of standard deviations is %f. The value is very small so the theoretical standard deviation is very close to population standard deviation.",differenceOfSD)

