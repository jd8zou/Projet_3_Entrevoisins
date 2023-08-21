package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class RemoveFavEvent {
    public Neighbour neighbour;

    public RemoveFavEvent(Neighbour neighbour) {

        this.neighbour = neighbour;

    }
}
