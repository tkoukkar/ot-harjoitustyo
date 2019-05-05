# Testausdokumentti

Sovelluksen toimivuutta on testattu sekä manuaalisesti että automaattisilla JUnit-testeillä. Käyttöliittymä on kuitenkin jätetty JUnit-testauksen ulkopuolelle ja testattu ainoastaan manuaalisesti.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Luokat SpriteTest ja SpaceshipTest testaavat vastaavasti luokkien Sprite ja Spaceship metodeja erityisesti sillä periaatteella, että yksi testi testaa vain yhtä asiaa. Lisäksi jälkimmäinen testaa myös luokkien Spaceship ja Sprite integraatiota; osassa tapauksista luokan Spaceship metodin kutsumisen kuuluu aiheuttaa kutsu jollekin vastaavan Sprite-olion metodille, minkä testit tarkastavat.

Varsinaista sovelluslogiikkaa ja eri luokkien integraatiota testaavat suoremmin luokat InputHandlingTest ja SpriteHandlingTest, jotka simuloivat käyttöliittymän vastaaville luokille (InputHandler ja SpriteHandler) lähettämiä kutsuja ja testaavat edelleen näiden luokkien eteenpäin (pääosin luokille Spaceship ja Sprite) lähettämien kutsujen toimintaa erilaisissa tilanteissa. Kaikilta osin testit eivät tosin aivan suoraan vastaa pelin normaalia toimintaa, sillä luotettavan testaamisen mahdollistamiseksi on pelissä normaalisti esiintyvä sattuman elementti jouduttu eliminoimaan, ja joissain tapauksissa tämä on ollut mahdollista vain luomalla tilanne, joka on pelin normaalin toiminnan näkökulmasta mahdoton (esim. osumien testaaminen täyttämällä ruutu rivillä ammuksia koko leveydeltään). Tällöinkin kuitenkin poikkeamat oikeassa pelissä mahdollisista tilanteista ovat kulloisessakin testissä testattavien metodien kannalta olennaisilta osin triviaaleja, joten itse metodien toiminnan testit pystyvät tarkastaamaan kohtuullisen luotettavasti.

### DAO-luokat

Luokalle SpaceshipDao ei ole omia testejä, mutta sen toiminta koostuu lähes pelkästään getter-metodeista, jotka kaikki tulevat testatuiksi sovelluslogiikan testeissä.

Luokka HighScoresTest testaa luokkaa HighScoreDao (sekä siihen liittyvää apuluokkaa Database) simuloimalla käyttöliittymän sille lähettämiä kutsuja.

### Testauskattavuus

Sovelluslogiikan osalta JUnit-testeillä on 94 % rivikattavuus ja 77 % haarautumakattavuus; DAO-luokkien osalta kumpikin on noin 80 %.

![testikattavuus](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/testikattavuus.png)

Luokka HighScoresTest testaa luokan HighScoreDao toiminnan vain yhdenlaisella testitietokannalla; testaamatta jäävät tilanteet, joissa tietokannassa on yli 20 tulosta sekä sellaiset, joissa tietokantaa ei ole. Myöskään uuden tuloksen lisäämistä tietokantaan ei nykyisessä toteutuksessa testata.

Sovelluslogiikan testeistä puuttuvat aluksen kiihdytyksen testaus sekä tietyt SpriteHandler-luokan haarat, joissa riippuvuus liittyy käsiteltävän Sprite-olion tyyppiin tai joissa tulos riippuu sattumasta.

## Järjestelmätestaus

### Asennus ja konfigurointi

Sovelluksen asennuksen ja konfiguroinnin toimivuus käyttöohjeen kuvaamalla tavalla on testattu Linux- ja Windows-ympäristöissä.

### Toiminnallisuus

Pelin toiminnallisuutta on testattu manuaalisesti pelaamalla sitä ja/tai selaamalla valikkoja vähintään kulloinkin relevanteilta osin (ja usein huomattavasti enemmänkin) aina uuden ominaisuuden lisäämisen jälkeen sekä lopullisella versiolla kahdella eri tietokoneella sekä Linux- että Windows-ympäristössä.
