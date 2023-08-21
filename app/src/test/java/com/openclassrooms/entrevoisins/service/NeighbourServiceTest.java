package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void createNeighbourTest() {
        Neighbour neighbourToCreate =  new Neighbour(16, "Francky", "https://sm.ign.com/ign_fr/cover/a/avatar-gen/avatar-generations_bssq.jpg", "Paris",
                "+33 7 58 74 11 22",  "Test ajout voisin, validé");
        service.createNeighbour(neighbourToCreate);
        assertTrue(service.getNeighbours().contains(neighbourToCreate));
    }

    @Test
    public void addFavoriteNeighbourTest() {
        Neighbour neighbourToFavorite = service.getNeighbours().get(0);
        service.addFavoriteNeighbour(neighbourToFavorite);
        assertTrue(service.getFavoriteNeighbours().contains(neighbourToFavorite));
    }

    @Test
    public void deleteFavoriteTest() {
        Neighbour neighbourDeleteFavorite = service.getNeighbours().get(0);
        service.deleteFavoriteNeighbour(neighbourDeleteFavorite);
        assertFalse(service.getFavoriteNeighbours().contains(neighbourDeleteFavorite));
    }

    @Test
    public void getFavoriteNeighbourTest() {
        Neighbour neighbour = service.getNeighbours().get(3);
        neighbour.setFavorite(true);
        assertTrue(service.getFavoriteNeighbours().contains(neighbour));
    }
}
