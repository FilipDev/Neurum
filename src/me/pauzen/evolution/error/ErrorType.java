/*
 *  Created by Filip P. on 5/23/16 9:43 PM.
 */

package me.pauzen.evolution.error;

public enum ErrorType {

    MEAN_SQUARE_ERROR((ideal, actual) -> {
        float total = 0.0F;
        for (int i = 0; i < ideal.length; i++) {
            total += Math.pow(ideal[i] - actual[i], 2);
        }
        return total / ideal.length;
    }),
    ROOT_MEAN_SQAURE_ERROR((ideal, actual) -> (float) Math.sqrt(MEAN_SQUARE_ERROR.calculator.calculate(ideal, actual)));

    protected final Calculator calculator;

    ErrorType(Calculator calculator) {

        this.calculator = calculator;
    }
}
