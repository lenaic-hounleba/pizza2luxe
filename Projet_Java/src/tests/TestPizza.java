package tests;
import pizzas.Ingredient;
import pizzas.Pizza;
import pizzas.TypePizza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;




/**
 * Tests JUnit de la classe {@link pizzas.Pizza
 * Pizza}.
 *
 * @author diamilatou-assura.diallo
 * @see pizzas.Pizza
 * @see pizzas.TypePizza
 * @see pizzas.Ingredient
 */
class TestPizza {
 


    private Pizza pizza;
    private Ingredient fromage;
    private Ingredient tomate;

    
    void setUp() {
        pizza = new Pizza("Margherita", TypePizza.Vegetarienne);
        fromage = new Ingredient("Fromage", 2.0);
        tomate = new Ingredient("Tomate", 1.0);
    }

    
    void testCreationPizza() {
        assertEquals("Margherita", pizza.getNom());
        assertEquals(TypePizza.Vegetarienne, pizza.getType());
        assertNotNull(pizza.getIngredients());
        assertEquals(0, pizza.getIngredients().size());
        assertEquals(0.0, pizza.getPrixVente());
    }

    
    void testAjouterIngredient() {
        pizza.getIngredients().add(fromage);

        
        assertEquals(1, pizza.getIngredients().size());
        assertTrue(pizza.getIngredients().contains(fromage));
    }

    
    void testPasDeDoublonIngredient() {
        pizza.getIngredients().add(fromage);
        pizza.getIngredients().add(fromage);

        assertEquals(1, pizza.getIngredients().size());
    }

   
    void testSetPrixVente() {
        pizza.setPrixVente(9.5);
        assertEquals(9.5, pizza.getPrixVente());
    }

    
    void testChangerNom() {
        pizza.setNom("Reine");
        assertEquals("Reine", pizza.getNom());
    }

    
    void testChangerType() {
        pizza.setType(TypePizza.Viande);
        assertEquals(TypePizza.Viande, pizza.getType());
    }

    
    void testPhoto() {
        pizza.setPhoto("margherita.jpg");
        assertEquals("margherita.jpg", pizza.getPhoto());
    }
}
