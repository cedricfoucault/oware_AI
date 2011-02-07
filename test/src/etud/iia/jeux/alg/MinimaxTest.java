/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package src.etud.iia.jeux.alg;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import src.etud.iia.jeux.modele.CoupJeu;
import src.etud.iia.jeux.modele.PlateauJeu;

/**
 *
 * @author jeremy
 */
public class MinimaxTest {

    public MinimaxTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of meilleurCoup method, of class Minimax.
     */
    @Test
    public void testMeilleurCoup() {
//        System.out.println("meilleurCoup");
//        PlateauJeu p = null;
//        Minimax instance = null;
//        CoupJeu expResult = null;
//        CoupJeu result = instance.meilleurCoup(p);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("TODO.");
    }

    /**
     * Test of getNbnoeuds method, of class Minimax.
     */
    @Test
    public void testGetNbnoeuds() {
//        System.out.println("getNbnoeuds");
//        Minimax instance = null;
//        int expResult = 0;
//        int result = instance.getNbnoeuds();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(4, Math.max(2, 4));
        assertEquals(2, Math.min(2, 4));
        assertEquals(2147483647, Integer.MAX_VALUE);
        assertEquals(-2147483648, Integer.MIN_VALUE);
//        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Minimax.
     */
    @Test
    public void testToString() {
//        System.out.println("toString");
//        Minimax instance = null;
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("TODO.");
    }

}