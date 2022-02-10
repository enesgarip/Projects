# Enes Garip - 150116034

import numpy as np
from sklearn.linear_model import LinearRegression
import random

# holds the values for the weights
test_x_axis = 0
test_y_axis = 0
test_points = 0


# linear regression helper function for question 12
def testq12(points, labels, total, n):
    regression = LinearRegression()
    regression.fit(points, labels)
    predictions = regression.predict(points)
    predictions = np.array(predictions > 0).astype(int) * 2 - 1
    total += np.array(predictions != labels).sum() / n
    return total


# Question 11 target function
def f1(a, b):
    return np.sign(a ** 2 + b ** 2 - 0.6)


# Question 4, 5, 6, 7 function
# y = equation
# j = slope
def f(a, b, x):
    j = (a[1] - b[1]) / (a[0] - b[0])
    k = a[1] - j * a[0]
    y = j * x + k
    return y


# Question 12 function
# Calculates all of the options in the question
def question12(n):
    x_axis = np.random.uniform(low=-1, high=1, size=n)
    y_axis = np.random.uniform(low=-1, high=1, size=n)
    points = np.array(list(zip(x_axis, y_axis)))
    total_sum_a = 0
    total_sum_b = 0
    total_sum_c = 0
    total_sum_d = 0
    total_sum_e = 0
    total_a = 0
    total_b = 0
    total_c = 0
    total_d = 0
    total_e = 0
    for _ in range(1000):
        # option A
        labels_a = np.sign(
            -1 - 0.05 * x_axis + 0.08 * y_axis + 0.13 * x_axis * y_axis + 1.5 * x_axis ** 2 + 1.5 * y_axis ** 2)
        total_sum_a += testq12(points, labels_a, total_a, n)
        # option B
        labels_b = np.sign(
            -1 - 0.05 * x_axis + 0.08 * y_axis + 0.13 * x_axis * y_axis + 1.5 * x_axis ** 2 + 15 * y_axis ** 2)
        total_sum_b += testq12(points, labels_b, total_b, n)
        # option C
        labels_c = np.sign(
            -1 - 0.05 * x_axis + 0.08 * y_axis + 0.13 * x_axis * y_axis + 15 * x_axis ** 2 + 1.5 * y_axis ** 2)
        total_sum_c += testq12(points, labels_c, total_c, n)
        # option D
        labels_d = np.sign(
            -1 - 1.5 * x_axis + 0.08 * y_axis + 0.13 * x_axis * y_axis + 0.05 * x_axis ** 2 + 0.05 * y_axis ** 2)
        total_sum_d += testq12(points, labels_d, total_d, n)
        # option E
        labels_e = np.sign(
            -1 - 0.05 * x_axis + 0.08 * y_axis + 1.5 * x_axis * y_axis + 0.15 * x_axis ** 2 + 0.15 * y_axis ** 2)
        total_sum_e += testq12(points, labels_e, total_e, n)

    print("-------------------------------------------")
    print("Q.12-a:", total_sum_a / 1000)
    print("Q.12-b:", total_sum_b / 1000)
    print("Q.12-c:", total_sum_c / 1000)
    print("Q.12-d:", total_sum_d / 1000)
    print("Q.12-e:", total_sum_e / 1000)
    print("-------------------------------------------")


# Question 11 function
def question11(n):
    x_axis = np.random.uniform(low=-1, high=1, size=n)
    y_axis = np.random.uniform(low=-1, high=1, size=n)
    points = np.array(list(zip(x_axis, y_axis)))

    total = 0
    for _ in range(1000):
        labels = f1(x_axis, y_axis)

        for i in random.sample(range(0, 1000), 100):
            labels[i] *= -1

        regression = LinearRegression()
        regression.fit(points, labels)
        predictions = regression.predict(points)
        predictions = np.array(predictions > 0).astype(int) * 2 - 1
        total += np.array(predictions != labels).sum() / n
    print("-------------------------------------------")
    print("Q.11:", total / 1000)
    print("-------------------------------------------")


# Question 9 function
def question9(n):
    x_axis = np.random.uniform(low=-1, high=1, size=n)  # Random x values with the size of training points
    y_axis = np.random.uniform(low=-1, high=1, size=n)  # Random y values with the size of training points
    points = np.array(list(zip(x_axis, y_axis)))
    total = 0
    for _ in range(1000):
        point_a, point_b = np.random.uniform(low=-1, high=1, size=(2, 2))

        data = f(point_a, point_b, x_axis)
        labels = np.array(data > y_axis).astype(int) * 2 - 1

        test_data = f(point_a, point_b, test_x_axis)
        test_label = np.array(test_data > test_y_axis).astype(int) * 2 - 1

        regression = LinearRegression()
        regression.fit(points, labels)
        predictions = regression.predict(test_points)
        predictions = np.array(predictions > 0).astype(int) * 2 - 1
        total += np.array(predictions != test_label).sum() / 100
    print("-------------------------------------------")
    print("Q.9: ", total / 1000)
    print("-------------------------------------------")


# Question 8 function
def question8(n):
    x_axis = np.random.uniform(low=-1, high=1, size=n)  # Random x values with the size of training points
    y_axis = np.random.uniform(low=-1, high=1, size=n)  # Random y values with the size of training points
    points = np.array(list(zip(x_axis, y_axis)))
    global test_x_axis, test_y_axis, test_points
    test_x_axis = x_axis
    test_y_axis = y_axis
    test_points = points
    total = 0
    for _ in range(1000):
        point_a, point_b = np.random.uniform(low=-1, high=1, size=(2, 2))

        data = f(point_a, point_b, x_axis)
        labels = np.array(data > y_axis).astype(int) * 2 - 1

        regression = LinearRegression()
        regression.fit(points, labels)
        predictions = regression.predict(points)
        predictions = np.array(predictions > 0).astype(int) * 2 - 1
        total += np.array(predictions != labels).sum() / 100
    print("-------------------------------------------")
    print("Q.8: ", total / 1000)
    print("-------------------------------------------")


# Question 4, 5, 6, 7 function
def question4567(n):
    point_a, point_b = np.random.uniform(low=-1, high=1, size=(2, 2))  # Random point generation for function f
    x_axis = np.random.uniform(low=-1, high=1, size=n)  # Random x values with the size of training points
    y_axis = np.random.uniform(low=-1, high=1, size=n)  # Random y values with the size of training points

    data = f(point_a, point_b, x_axis)
    labels = 2 * np.array(data > y_axis).astype(int) - 1 # It sets boundaries for labels -1 to 1.
    points = np.array(list(zip(x_axis, y_axis, np.ones(n))))
    permutations = np.random.permutation(n)

    convergence = 0
    disagreement = 0
    total_iteration = 0
    # Repeating the process 1000 times to get reliable estimation
    for _ in range(1000):
        i = 0
        g = []
        w = np.zeros(3)  # initial weights vector that all values are zeros.
        while True:
            point = np.array(points[i])
            label = labels[i]
            prediction = np.sign(np.dot(w, point))
            g.append(prediction)  # g holds the predicted values

            if i == n - 1:
                total = np.sum(labels == np.array(g))
                missed = n - total
                disagreement += missed / n
                total_iteration += 1
                if total == n:
                    break
            if prediction != label:
                w = w + point * label
            convergence += 1
            i += 1
            if i % n == 0:
                np.random.shuffle(permutations)
                i = 0
                g = []
    print(f'\n----- Q.4, Q.5, Q.6, Q.7 Experiment with N = {n} -----')
    print("Convergence Ratio:", convergence / 1000)
    print("Disagreement Ratio:", disagreement / total_iteration)
    print("Total Iteration:", total_iteration)
    print("-------------------------------------------------------")

# Functions will be executed
# the argument of the functions = N
question4567(10)
question4567(100)
question8(100)
question9(1000)
question11(1000)
question12(1000)
