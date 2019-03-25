package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(1);
        kortti.lataaRahaa(2);
        kortti.lataaRahaa(3);
        assertEquals(16, kortti.saldo());
    }
    
    @Test
    public void rahanOttaminenPalauttaaTrueJosRahaRiittaa() {
        assertTrue(kortti.otaRahaa(10));
    }
    
    @Test
    public void rahanOttaminenPalauttaaFalseJosRahaEiRiita() {
        assertFalse(kortti.otaRahaa(11));
    }
    
    @Test
    public void rahanOttaminenVahentaaSaldoa() {
        kortti.otaRahaa(1);
        kortti.otaRahaa(2);
        kortti.otaRahaa(3);
        assertEquals(4, kortti.saldo());
    }
    
    @Test
    public void rahanOttaminenEiVahennaSaldoaNegatiiviseksi() {
        kortti.otaRahaa(4);
        kortti.otaRahaa(5);
        kortti.otaRahaa(6);
        assertEquals(1, kortti.saldo());
    }
    
    @Test
    public void tulostusToimii() {
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.5", kortti.toString());
    }
}
