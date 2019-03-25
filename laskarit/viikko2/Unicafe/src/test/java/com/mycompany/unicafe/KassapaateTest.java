package com.mycompany.unicafe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tkoukkar
 */
public class KassapaateTest {
    Kassapaate paate;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(500);
    }
    
    @Test
    public void konstruktoriLuoPohjakassanOikein() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void konstruktoriEiMyyEdullisia() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void konstruktoriEiMyyMaukkaita() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    // KÄTEISTESTIT
    // ------------
    
    @Test
    public void syoEdullisestiKasvattaaMyytyjenEdullistenMaaraa() {
        paate.syoEdullisesti(240);
        paate.syoEdullisesti(240);
        paate.syoEdullisesti(240);
        
        assertEquals(3, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKasvattaaMyytyjenMaukkaidenMaaraa() {
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(400);
        
        assertEquals(3, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiLisaaSaldoaOikein() {
        paate.syoEdullisesti(240);
        paate.syoEdullisesti(240);
        
        assertEquals(paate.kassassaRahaa(), 100480);
    }
    
    @Test
    public void syoMaukkaastiLisaaSaldoaOikein() {
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(400);
        
        assertEquals(paate.kassassaRahaa(), 100800);
    }
    
    @Test
    public void syoEdullisestiPalauttaaVaihtorahat() {
        int vaihtorahat = 0;
        
        vaihtorahat += paate.syoEdullisesti(300);
        vaihtorahat += paate.syoEdullisesti(300);
        vaihtorahat += paate.syoEdullisesti(241);
        
        assertEquals(121, vaihtorahat);
    }
    
    @Test
    public void syoMaukkaastiPalauttaaVaihtorahat() {
        int vaihtorahat = 0;
        
        vaihtorahat += paate.syoMaukkaasti(500);
        vaihtorahat += paate.syoMaukkaasti(500);
        vaihtorahat += paate.syoMaukkaasti(401);
        
        assertEquals(201, vaihtorahat);
    }
    
    @Test
    public void syoEdullisestiEiMyyJosRahaEiRiita() {
        paate.syoEdullisesti(1);
        paate.syoEdullisesti(239);
        paate.syoEdullisesti(120);
        
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiEiMyyJosRahaEiRiita() {
        paate.syoMaukkaasti(1);
        paate.syoMaukkaasti(240);
        paate.syoMaukkaasti(399);
        
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiEiKasvataSaldoaJosEiMyy() {
        paate.syoEdullisesti(1);
        paate.syoEdullisesti(239);
        paate.syoEdullisesti(120);
        
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiEiKasvataSaldoaJosEiMyy() {
        paate.syoMaukkaasti(1);
        paate.syoMaukkaasti(240);
        paate.syoMaukkaasti(399);
        
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiPalauttaaRahatJosEiMyy() {
        int vaihtorahat = 0;
        
        vaihtorahat += paate.syoEdullisesti(1);
        vaihtorahat += paate.syoEdullisesti(239);
        vaihtorahat += paate.syoEdullisesti(120);
        
        assertEquals(360, vaihtorahat);
    }
    
    @Test
    public void syoMaukkaastiPalauttaaRahatJosEiMyy() {
        int vaihtorahat = 0;
    
        vaihtorahat += paate.syoMaukkaasti(1);
        vaihtorahat += paate.syoMaukkaasti(240);
        vaihtorahat += paate.syoMaukkaasti(399);
        
        assertEquals(640, vaihtorahat);
    }
    
    // KORTTITESTIT
    // ************
    
    @Test
    public void syoEdullisestiPalauttaaTrueJosKortinSaldoRiittaa() {
        assertTrue(paate.syoEdullisesti(kortti));
    }
    
    public void syoMaukkaastiPalauttaaTrueJosKortinSaldoRiittaa() {
        assertTrue(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void syoEdullisestiVeloittaaKortinSaldoaOikein() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        
        assertEquals(20, kortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiVeloittaaKortinSaldoaOikein() {
        paate.syoMaukkaasti(kortti);
        
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void syoEdullisestiKortillaKasvattaaMyytyjenEdullistenMaaraa() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        
        assertEquals(2, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKortillaKasvattaaMyytyjenMaukkaidenMaaraa() {
        paate.syoMaukkaasti(kortti);
         
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiEiLisaaKassaanRahaaKorttiostolla() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiEiLisaaKassaanRahaaKorttiostolla() {
        paate.syoMaukkaasti(kortti);
        
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiPalauttaaFalseJosKortinSaldoEiRiita() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        
        assertFalse(paate.syoEdullisesti(kortti));
    }
        
    @Test
    public void syoMaukkaastiPalauttaaFalseJosKortinSaldoEiRiita() {
        paate.syoMaukkaasti(kortti);
        
        assertFalse(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void syoEdullisestiEiMyyJosKortinSaldoEiRiita() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        // saldo on nyt 20, edullisia myyty 2
        paate.syoEdullisesti(kortti);
        
        assertEquals(2, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiEiMyyJosKortinSaldoEiRiita() {
        paate.syoMaukkaasti(kortti);
        // saldo on nyt 100, maukkaita myyty 1
        paate.syoMaukkaasti(kortti);
        
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiEiVeloitaKorttiaJosSaldoEiRiita() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        // saldo on nyt 20
        paate.syoEdullisesti(kortti);
        
        assertEquals(20, kortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiEiVeloitaKorttiaJosSaldoEiRiita() {
        paate.syoMaukkaasti(kortti);
        // saldo on nyt 100
        paate.syoMaukkaasti(kortti);
        
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void lataaRahaaKortilleLisaaKortinSaldoaPositiivisellaSummalla() {
        paate.lataaRahaaKortille(kortti, 1);
        paate.lataaRahaaKortille(kortti, 10);
        paate.lataaRahaaKortille(kortti, 100);
        paate.lataaRahaaKortille(kortti, -100);
        
        assertEquals(611, kortti.saldo());
    }

    
    
}
