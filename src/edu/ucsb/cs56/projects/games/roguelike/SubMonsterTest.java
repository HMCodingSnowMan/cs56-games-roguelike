package edu.ucsb.cs56.projects.games.roguelike;



import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/** test class for my subclasses
    @author Hans Marasigan & Richard Nguyen
    @version cs56 S13,lab04
    @see Troll,Golem,Bat
*/

public class SubMonsterTest{
        @Test
	public void test_defaultConstructor_and_GettersBat(){
	    Bat b=new Bat();
	    assertEquals(10,b.getHitPoints());
	    assertEquals(10,b.getAttack());
	    assertEquals(10,b.getPointValue());
	}
        @Test
	public void test_defaultConstructor_and_GettersTroll(){
	    Troll t=new Troll();
	    assertEquals(15,t.getHitPoints());
	    assertEquals(10,t.getAttack());
	    assertEquals(15,t.getPointValue());
	}
        @Test
	public void test_defaultConstructor_and_GettersGolem(){
	    Golem g=new Golem();
	    assertEquals(50,g.getHitPoints());
	    assertEquals(20,g.getAttack());
	    assertEquals(20,g.getPointValue());
	}
        @Test
	public void test_3ArgConstructorBat(){
	    Bat b=new Bat(20,24,0);
	    assertEquals(20,b.getHitPoints());
	    assertEquals(24,b.getAttack());
	    assertEquals(10,b.getPointValue());
	}
        @Test
	public void test_3ArgConstructorTroll(){
	    Troll t=new Troll(20,25,0);
	    assertEquals(20,t.getHitPoints());
	    assertEquals(25,t.getAttack());
	    assertEquals(15,t.getPointValue());
	}
        @Test
	public void test_3ArgConstructorGolem(){
	    Golem g=new Golem(50,40,0);
	    assertEquals(50,g.getHitPoints());
	    assertEquals(40,g.getAttack());
	    assertEquals(20,g.getPointValue());
	}
        @Test
	public void test_4argConstructorBat(){
	    Bat b=new Bat(20,30,0,40);
	    assertEquals(20,b.getHitPoints());
	    assertEquals(30,b.getAttack());
	    assertEquals(40,b.getPointValue());
	}

        @Test
	public void test_4argConstructorTroll(){
	    Troll t=new Troll(30,20,0,40);
	    assertEquals(30,t.getHitPoints());
	    assertEquals(20,t.getAttack());
	    assertEquals(40,t.getPointValue());
	}
        @Test
	public void test_4argConstructorGolem(){
	    Golem g=new Golem(50,40,0,30);
	    assertEquals(50,g.getHitPoints());
	    assertEquals(40,g.getAttack());
	    assertEquals(30,g.getPointValue());
	}
    
        @Test
	public void test_monster4arg(){
	    Monster m=new Monster(10,20,0,30);
	    assertEquals(10,m.getHitPoints());
	    assertEquals(20,m.getAttack());
	    assertEquals(30,m.getPointValue());

	}
    @Test 
    public void test_MonsterSetter(){
	Monster m = new Monster();
	m.setHitPoints(1);
	assertEquals(1,m.getHitPoints());
	m.setAttack(2);
	assertEquals(2,m.getAttack());
	m.setPointValue(3);
	assertEquals(3,m.getPointValue());
    }
    @Test
    public void test_BatSetter(){
        Bat m = new Bat();
        m.setHitPoints(1);
        assertEquals(1,m.getHitPoints());
        m.setAttack(2);
        assertEquals(2,m.getAttack());
        m.setPointValue(3);
        assertEquals(3,m.getPointValue());
    }


    @Test
    public void test_TrollSetter(){
        Troll t = new Troll();
        t.setHitPoints(1);
        assertEquals(1,t.getHitPoints());
        t.setAttack(2);
        assertEquals(2,t.getAttack());
        t.setPointValue(3);
        assertEquals(3,t.getPointValue());
    }


    @Test
    public void test_GolemSetter(){
        Golem g = new Golem();
        g.setHitPoints(1);
        assertEquals(1,g.getHitPoints());
        g.setAttack(2);
        assertEquals(2,g.getAttack());
        g.setPointValue(3);
        assertEquals(3,g.getPointValue());
    }


}
