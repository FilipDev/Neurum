/*
 *  Created by Filip P. on 5/21/16 6:27 PM.
 */

package me.pauzen.evolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Selector {

    private int populationSize = 500;
    private int selectionSize  = 25;
    private double max;
    private double aValue;

    public Selector(int selectionSize, int populationSize) {
        this.max = 3 * populationSize / 2 / selectionSize;
        this.aValue = -max / selectionSize / selectionSize;
        this.selectionSize = selectionSize;
        this.populationSize = populationSize;
    }

    public void select(List<Creature> selected) {


    }

    public Collection<Creature> clone(List<Creature> selected) {
        List<Creature> creatures = new ArrayList<>();
        for (int i = 0; i < selected.size(); i++) {
            int copies = (int) Math.round(aValue * (i * i + i) + max + 2.0D * i / selectionSize - 1.0D);
            for (int copy = 0; copy < copies; copy++) {
                creatures.add(selected.get(i).clone());
            }
        }
        return creatures;
    }

}
