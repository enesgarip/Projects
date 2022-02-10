# Enes Garip - 150116034
# Homework 3
import math
import numpy as np


def f_without_regularization(train_data_x, train_data_y, test_data_x, test_data_y):
    formula = np.dot(np.linalg.inv(np.dot(train_data_x.T, train_data_x)), train_data_x.T)
    w = np.dot(formula, train_data_y)
    input_data = np.sign(np.dot(train_data_x, w))
    e_in = 1 - np.mean(input_data == train_data_y)
    output_data = np.sign(np.dot(test_data_x, w))
    e_out = 1 - np.mean(output_data == test_data_y)
    return e_in, e_out


def f_with_regularization(x, train_data_x, train_data_y, test_data_x, test_data_y):
    formula = np.dot(np.linalg.inv(np.dot(train_data_x.T, train_data_x) + 10 ** x * np.identity(8)), train_data_x.T)
    w = np.dot(formula, train_data_y)
    input_data = np.sign(np.dot(train_data_x, w))
    e_in = 1 - np.mean(input_data == train_data_y)
    output_data = np.sign(np.dot(test_data_x, w))
    e_out = 1 - np.mean(output_data == test_data_y)
    return e_in, e_out


def hw6q23456():
    train_data = np.loadtxt('in.dta')
    test_data = np.loadtxt('out.dta')

    x_train, y_train = train_data[:, :2], train_data[:, 2]
    x_test, y_test = test_data[:, :2], test_data[:, 2]

    n_train = len(x_train)
    x_train = np.array([np.ones(n_train),
                        x_train[:, 0],
                        x_train[:, 1],
                        x_train[:, 0] ** 2,
                        x_train[:, 1] ** 2,
                        x_train[:, 0] * x_train[:, 1],
                        np.absolute(x_train[:, 0] - x_train[:, 1]),
                        np.absolute(x_train[:, 0] + x_train[:, 1])
                        ]).T
    n_test = len(x_test)
    x_test = np.array([np.ones(n_test),
                       x_test[:, 0],
                       x_test[:, 1],
                       x_test[:, 0] ** 2,
                       x_test[:, 1] ** 2,
                       x_test[:, 0] * x_test[:, 1],
                       np.absolute(x_test[:, 0] - x_test[:, 1]),
                       np.absolute(x_test[:, 0] + x_test[:, 1])
                       ]).T

    q2e_in, q2e_out = f_without_regularization(x_train, y_train, x_test, y_test)
    print(f'Q2) Ein = {q2e_in} Eout = {q2e_out}')

    q3e_in, q3e_out = f_with_regularization(-3, x_train, y_train, x_test, y_test)
    print(f'Q3) Ein = {q3e_in} Eout = {q3e_out}')

    q4e_in, q4e_out = f_with_regularization(3, x_train, y_train, x_test, y_test)
    print(f'Q4) Ein = {q4e_in} Eout = {q4e_out}')
    print("Q5) ")
    for i in range(-2, 3):
        q5e_in, q5e_out = f_with_regularization(i, x_train, y_train, x_test, y_test)
        print(f'\tk = {i} Ein = {q5e_in} Eout = {q5e_out}')


def hw5q89():
    pass


# HW-5 Question 4, 5 ,6 ,7 Implementation
def hw5q4567():
    # Error calculation
    def E(u, v):
        return (u * math.e ** v - 2 * v * math.e ** (-u)) ** 2

    # Gradient u calculation
    def gradient_u(u, v):
        return 2 * (u * math.e ** v - 2 * v * math.e ** (-u)) * (math.e ** v + 2 * v * math.e ** (-u))

    # Gradient v calculation
    def gradient_v(u, v):
        return 2 * (u * math.e ** v - 2 * v * math.e ** (-u)) * (u * math.e ** v - 2 * math.e ** (-u))

    # Gradient descent calculation
    def gradient_descent(u, v, learning_ratio):
        i = 0
        while True:
            i += 1

            du = gradient_u(u, v)
            dv = gradient_v(u, v)

            u = u - learning_ratio * du
            v = v - learning_ratio * dv

            if E(u, v) < 10 ** (-14):
                break

        return i, u, v

    # Coordinate descent calculation
    def coordinate_descent(u, v, learning_ratio):
        for _ in range(15):
            du = gradient_u(u, v)
            u = u - learning_ratio * du

            dv = gradient_v(u, v)
            v = v - learning_ratio * dv

        return E(u, v)

    # u and v values and learning rate
    u, v = (1, 1)
    learning_rate = 0.1

    iteration, new_u, new_v = gradient_descent(u, v, learning_rate)
    print(f'Iteration = {iteration}')
    print(f'New u = {new_u}\tNew v = {new_v}')

    error = coordinate_descent(u, v, learning_rate)
    print(f'Error after 15 iterations = {error}')


hw5q4567()
hw5q89()
hw6q23456()
