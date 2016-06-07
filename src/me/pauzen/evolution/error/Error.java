/*
 *  Created by Filip P. on 5/23/16 9:53 PM.
 */

package me.pauzen.evolution.error;

public final class Error {

    private Error() {
    }

    public static float calculateError(ErrorType method, float[] ideal, float[] actual) {
        return Error.calculateError(method.calculator, ideal, actual);
    }

    public static float calculateError(Calculator method, float[] ideal, float[] actual) {
        return method.calculate(ideal, actual);
    }

}
