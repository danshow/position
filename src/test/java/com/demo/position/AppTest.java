package com.demo.position;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void testDisplay(){
        Map<String,Integer> positions=new HashMap<>(10);
//        App.display(positions);
    }
    @Test
    public void testModifyMap(){
        Map map=new HashMap();
        map.put("aaa",111);
        map.put("bbb",112);
        map.put("aaa",11);
        System.out.println(map);
    }
}
