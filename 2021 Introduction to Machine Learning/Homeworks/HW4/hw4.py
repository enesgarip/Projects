import pandas as pd
import numpy as np
from sklearn.multiclass import OneVsRestClassifier, OneVsOneClassifier
from sklearn.svm import SVC


def question56(t, t_x, t_y, t_x2, t_y2):
    for c in t:
        _svm = SVC(C=c, degree=2, coef0=1, kernel="poly", gamma=1, probability=True, random_state=0)
        cls = OneVsOneClassifier(_svm)
        cls.fit(t_x, t_y)
        print(f'--- C = {c} ---\nEin = {1 - cls.estimators_[12].score(t_x, t_y)}\n'
              f'Eout = {1 - cls.estimators_[12].score(t_x2, t_y2)}\n'
              f'Vectors = {cls.estimators_[12].n_support_.sum()}')


def print_results(x, cl, t_x, t_y):
    print(f'Ein for {x}: ' + str(1 - cl.estimators_[x].score(t_x, t_y)))


train_file = pd.read_csv("features.train", delim_whitespace=True, names=["d", "i", "s"])
test_file = pd.read_csv("features.test", delim_whitespace=True, names=["d", "i", "s"])

train_x = train_file[["i", "s"]].to_numpy()
train_y = train_file["d"].values

test_x = test_file[["i", "s"]].to_numpy()
test_y = test_file["d"].values

svm = SVC(C=0.01, degree=2, coef0=1, kernel="poly", gamma=1, probability=True)
classifier = OneVsRestClassifier(svm)
classifier.fit(train_x, train_y)
question2 = [0, 2, 4, 6, 8]
question3 = [1, 3, 5, 7, 9]

print("-----  Question 2  -----")
for i in question2:
    print_results(i, classifier, train_x, train_y)

print("\n-----  Question 3  -----")
for i in question3:
    print_results(i, classifier, train_x, train_y)

print("\n-----  Question 4  -----")
n_supp0 = classifier.estimators_[0].n_support_.sum()
n_supp1 = classifier.estimators_[1].n_support_.sum()
print("Difference = ", n_supp0 - n_supp1)

print("\n-----  Question 5  -----")
question5 = [0.001, 0.01, 0.1, 1]
question56(question5, train_x, train_y, test_x, test_y)

print("\n-----  Question 6  -----")
question6 = [0.0001, 0.001, 0.01, 1]
question56(question6, train_x, train_y, test_x, test_y)

print("\n-----  Question 7  -----")
print("\n-----  Question 8  -----")
print("\n-----  Question 9  & Question 10 -----")
