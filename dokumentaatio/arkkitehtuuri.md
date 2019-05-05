# Arkkitehtuurikuvaus

## Rakenne

Ohjelmakoodi on jaettu kolmeen pakkaukseen: *murikat.gui, murikat.logics* ja *murikat.dao*. Näistä *murikat.gui* sisältää käyttöliittymän, *murikat.logics* sovelluslogiikan ja *murikat.dao* datan hakemisesta ja tallentamisesta ulkoisiin tiedostoihin vastaavat luokat. Rakennetta kuvaa seuraava luokka- ja pakkauskaavio:

![pakkauskaavio](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/pakkauskaavio.jpg)

## Käyttöliittymä

Käyttöliittymä sisältää kolme erillistä näkymää: päävalikon, piste-ennätyslistan sekä varsinaisen pelinäkymän. Kutakin näistä edustaa erillinen *Scene*-olio, jonka yksi *MurikatUi*-luokan metodeista luo ja asettaa näkyväksi eli sijoittaa ohjelman *Stageen*. Pelinäkymän luova metodi *run()* vastaa lisäksi käyttäjän näppäinkomentojen välittämisestä sovelluslogiikalle, sovelluslogiikan tarvittavien metodien kutsumisesta jokaisen ruudunpäivityksen yhteydessä sekä pelinäkymän päivittämisestä pelitilanteen mukaan.

## Tiedon tallennus ja hakeminen

Pakkauksessa *murikat.dao* sijaitsee kaksi DAO-luokkaa (*Data Access Object*), joita kumpaakin edustaa ohjelman normaalin suorituksen aikana yksi olio. Nämä oliot huolehtivat tiedon hakemisesta ulkoisista tiedostoista sekä uusien ennätystulosten tapauksessa myös niiden tallentamisesta ulkoiseen tiedostoon. Ohjelman käyttämien ulkoisten tiedostojen nimet ja polut on tallennettu sen juurihakemistossa sijaitsevaan tiedostoon ![config.properties](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/Murikat/config.properties).

Ennätystulokset tallennetaan SQL-tietokantaan, joka oletusarvoisesti sijaitsee tiedostossa ![/data/scores.db](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/Murikat/data/scores.db). SQL-komennot välittää tietokannalle luokka *HighScoreDao*; tietokantayhteyden ylläpitoon käytetään toisteisen koodin välttämiseksi lisäksi apuluokkaa *Database*. Tietokannasta haettu data kirjataan ArrayList-listoille, joiden getter-metodeja kutsumalla käyttöliittymän ennätysnäkymän luova metodi muodostaa ennätysnäkymässä näkyvät listat.

Pelaajan avaruusaluksen ominaisuuksia kuvaavien muuttujien arvot haetaan nykyisessä toteutuksessa suurelta osin (ja tulevaisuudessa mahdollisesti kokonaan) ulkoisesta datatiedostosta (oletusarvoisesti ![/data/spaceship.dat](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/Murikat/data/spaceship.dat)). Tiedot hakee *SpaceshipDao*-olio, joka tallentaa ne omien muuttujiensa arvoiksi ja jonka getter-metodeja kutsumalla sovelluslogiikka saa ne käyttöönsä.

## Sovelluslogiikka

Pelin aikana animaatiota pyörittää käyttöliittymän sisältämä *Timeline*-olio, joka kutsuu joka ruudunpäivityksen aikana sovelluslogiikan luokkien tarvittavia metodeja.

Kutakin pelin liikkuvista kappaleista kuvaa luokkaan *Sprite* kuuluva olio. Luokka sisältää tiedon kyseistä kappaletta tietokoneen ruudulla esittävän monikulmion muodosta ja väristä, sekä muuttujat, joiden avulla pidetään kirjaa sen sijainnista, orientaatiosta, liikesuunnasta ja -nopeudesta. Lisäksi luokka sisältää tiedon kappaleen tilasta pelissä; kullakin kappaleella on aluksi tietty määrä *osumapisteitä* (yleensä oletusarvoisesti yksi, pelaajan avaruusaluksella kuitenkin kolme), jotka vähenevät tietyin ehdoin kappaleiden törmätessä ja joiden loppuessa kappale merkitään tuhoutuneeksi. Luokan metodit ovat pääosin gettereitä ja settereitä, jotka vastaavasti mahdollistavat kappaleen sijainnin ja liikkeen ym. ominaisuuksien muuttamisen.

Pelaajan avaruusalusta kuvaa lisäksi luokan *Spaceship* ainoa olio, joka pääosin välittää tietyin parametrein kutsuja vastaavalle *Sprite*-oliolle. Parametrien arvot luokka puolestaan hakee kutsumalla em. *murikat.dao*-pakkaukseen kuuluvan luokan SpaceshipDao getter-metodeja.

*InputHandler*-luokan ainoa olio käsittelee pelaajan antamat näppäinkomennot ja kutsuu niiden mukaan *Spaceship*-olion käännös- tai kiihdytysmetodeja. Lisäksi *InputHandler* merkitsee tarvittaessa liipaisimen painetuksi tai vapautetuksi, minkä perusteella muut luokat ohjaavat aluksen aseen toimintaa.

Pelin keskeisestä toiminnasta vastaa puolestaan luokan *SpriteHandler* ainoa olio, joka pitää kirjaa pelissä olevistä *Sprite*-olioista ja kutsuu niiden metodeja pelitilanteen mukaan. *SpriteHandlerin* apuluokkana toimii *RockRegistry*, joka pitää kirjaa pelissä olevista murikoista sekä niiden tilasta ja *sukupolvesta* eli siitä, onko kyseessä uusi murikka (1. sukupolvi) vai sellaisesta lohkeamalla syntynyt kappale (2. ja 3. sukupolvi).

Jokaisen ruudunpäivityksen yhteydessä kutsutaan ensin *SpriteHandler*-olion metodia, joka käsittelee kappaleiden liikkeen, ja tämän jälkeen kappaleiden mahdolliset törmäykset ja tuhoutumisen käsitteleviä metodeja. Tuhoutuneet kappaleet poistetaan pelistä, ja mikäli pelaajan avaruusalus ei tuhoutumisen käsittelyn jälkeen ole enää pelissä, peli päättyy.

Jos pelaajalla on pelin päättyessä riittävästi pisteitä, käyttöliittymä siirtää näkymän piste-ennätyslistalle ja mahdollistaa pelaajan nimen lisäämisen listaan. Muussa tapauksessa näkymä palautuu pelin loputtua päävalikkoon.


### Keskeiset toiminnot

#### Aluksen ohjaaminen

Pelinäkymää luotaessa käyttöliittymä määrittelee näkymän sisältävän *Scene*-olion *setOnKeyPressed()*-toiminnan siten, että kyseisen *Scenen* ollessa ohjelman *Stagella* käyttäjän painamien näppäimien näppäinkoodit välittyvät *InputHandler*-olion metodille *input(KeyCode)*, joka tallentaa ne kyseisen olion sisältämään painettujen näppäimien listaan. Vastaavasti tämän *Scene*-olion *setOnKeyReleased()* asetetaan välittämään vapautettujen näppäimien koodit *InputHandlerin* metodille *remove(KeyCode)*, joka poistaa näppäinkoodin listalta. Jos vapautettu näppäin on liipaisin (välilyönti), nollaa tämä metodi lisäksi liipaisimen tilaa kuvaavan muuttujan *triggerState* arvon.

Joka ruudunpäivityksen yhteydessä käyttöliittymä kutsuu ensimmäiseksi *InputHandler*-olion metodia *processControls()*, joka käy läpi painettujen näppäimien listan sellaisena, kuin se on kyseisen ruudunpäivityksen aikana, ja tarpeen mukaan kutsuu aluksen kiihdytysmetodia tai sen orientaatiota muuttavia metodeja (käännökset), tai liipaisimen ollessa painettuna kasvattaa *triggerState*-muuttujan arvoa.

Aluksen kääntyminen päivittyy pelinäkymään suoraan *Sprite*-olion *setRotate(double)*-metodin välityksellä:



*InputHandler*-olion metodien suorituksen jälkeen käyttöliittymä kutsuu *SpriteHandler*-olion metodia *processMovement()* (tai tietyissä tilanteissa sitä ennen metodia *processFiring()*; ks. alla). Tämä käy yksitellen läpi kaikki pelissä olevat *Sprite*t selvittäen niiden nykyisen sijainnin ja liikkeen sekä määrittäen niille uuden näiden perusteella uuden sijainnin. Esimerkkinä tilanne, jossa pelaajan avaruusalus lähtee liikkeelle pelin alussa (yksinkertaisuuden vuoksi kaaviosta on jätetty pois pelissä olevat murikat, joista kutakin kuvaavan *Spriten* vastaavia liikkumismetodeja myös kutsutaan saman processMovement()-metodin aikana):

![sekvenssikaavio](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio_murikat_viikko5.png)

#### Ampuminen

Jos yllä kuvatun *triggerState*-muuttujan arvo on välittömästi *InputHandler*-olion metodien käsittelemisen jälkeen tasan yksi, kutsuu käyttöliittymä seuraavaksi *SpriteHandler*-olion metodia *processFiring*. Käytännössä tämä tarkoittaa sitä, että ampuminen käsitellään vain siinä tilanteessa, että pelaaja on juuri painanut liipaisinta; liipaisimen pitäminen pohjassa kasvattaa *triggerState*-muuttujan arvoa jokaisen ruudunpäivityksen yhteydessä, mutta sen arvon kasvaessa yli mainitun yhden mitään ei tapahdu. Uudelleen ampuakseen pelaajan tulee siis vapauttaa liipaisin (jolloin *triggerState* nollautuu) ja painaa sitä uudelleen.

Metodi *processFiring()* tutkii, montako ammusta pelissä jo on; jos niitä on alukselle asetetun maksimiarvon verran (oletusarvoisesti kolme) tai enemmän, mitään ei tapahdu. Muussa tapauksessa se kutsuu *Spaceship*-olion metodia *fire()*, joka puolestaan kutsuu alusta vastaavan *Sprite*-olion metodia *emitProjectile(Polygon, double, double)* parametreina ammusta esittävä monikulmio, aluksen keulan osoittamaa suuntaa kuvaava kerroin 0, sekä ulkoisesta datatiedostosta haettu aluksen aseen teho. Viimeksi mainittu metodi luo ammusta vastaavan *Sprite*-olion ja asettaa sille sijainnin, suunnan ja nopeuden vastaavasti itse aluksen sijainnin ja orientaation sekä parametrina saadun aseen tehon mukaan. Tämän jälkeen luotu *Sprite* palautetaan *Spaceship*-olion metodille *fire()*, joka puolestaan palauttaa sen *SpriteHandler*-olion metodille *processFiring()*. Lopuksi *processFiring()* lisää kyseisen *Spriten* käsiteltävien *Sprite*-olioiden listalle sekä erilliselle ammuslistalle ja asettaa sitä kuvaavan monikulmion näkyväksi ruudulle.



#### Törmäykset

Kun kappaleiden liike on käsitelty, käyttöjärjestelmä kutsuu seuraavaksi *SpriteHandler*-olion metodia *processCollisions()*, joka tutkii, onko kaksi erityyppistä *Sprite*-oliota liikkeen seurauksena törmännyt toisiinsa. Metodi käy läpi kunkin pelissä olevista murikoista ja tutkii, onko sitä kuvaavan monikulmion rajojen sisällä pelaajan avaruusaluksen tai jonkin pelissä pelissä olevan ammuksen (eli *SpriteHandler*-olion ammuslistalle kirjatun *Spriten*) sijainti.

Ammuksen törmätessä murikkaan vähennetään sekä ammusta että osuman saanutta murikkaa kuvaavan *Spriten* osumapisteitä ja merkitään murikka poistettavaksi *RockRegistry*-olion listalta kutsumalla metodia *setHitRock(Sprite)* (parametrina kyseinen murikka). Lisäksi mikäli kyseessä on ensimmäisen tai toisen sukupolven murikka, kutsutaan murikan kahtia halkaisevaa metodia *sunder(Sprite)* (parametrina niinikään kyseinen murikka). Murikan halkaiseminen käsitellään pitkälti samoin kuin edellä kuvattu ampuminen: metodi *sunder* luo halkeamalla syntyneet kappaleet, asettaa niille suunnan sekä nopeuden ja kutsuu tämän jälkeen kahdesti halkeavaa murikkaa kuvaavan *Sprite*-olion metodia *emitProjectile(Polygon, double, double)*. Syntyneet kappaleet lisätään käsiteltävien *Sprite*-olioiden joukkoon ja niitä kuvaavat monikulmiot ruudulle. Erona ammuksiin on se, että uusia murikoita ei merkitä ammuslistalle; sen sijaan ne lisätään *RockRegistry*-olion listalle kutsumalla sen metodia *add(Sprite)* (parametrina lisättävä murikka). Lopuksi alkuperäinen halkeavaksi merkitty murikka poistetaan *RockRegistryn* listalta kutsumalla sen metodia *remove(Sprite)*.



Törmäys murikan ja pelaajan avaruusaluksen käsitellään kutsumalla *Spaceship*-olion metodia *collide()*, joka vähentää aluksen suojia ja vastaavasti sitä kuvaavan *Sprite*-olion osumapisteitä. Lisäksi se asettaa aluksen hetkeksi aikaa haavoittumattomaan tilaan (osoitetaan ruudulla värjäämällä alustava esittävä monikulmio punaiseksi), jotta samaa törmäystä ei käsitellä kahdesti.


#### Kappaleiden tuhoutuminen

Kun törmäykset on käsitelty, käyttöliittymä kutsuu *SpriteHandler*-olion metodia *processDestruction()*, joka käy läpi pelissä olevat *Sprite*-oliot ja tutkii niiden osumapisteet. Jos osumapisteitä on alle yksi, kutsutaan kyseisen *Spriten* metodia *destroy()*, joka merkitsee *Spriten* tuhoutuneeksi ja poistaa sitä kuvaavan monikulmion ruudulta. Lopuksi metodi käy uudelleen läpi kaikki *Sprite*-oliot ja poistaa joko tämän metodin aikana tai aiemmin tuhoutuneeksi merkityt *Spritet* käsiteltävien listoilta.

Lisäksi metodi *processDestruction()* palauttaa käyttöliittymälle tiedon siitä, onko pelaajan avaruusalus metodin suorittamisen jälkeen yhä pelissä (palautusarvo *true*) vai ei (*false*). Jälkimmäisessä tapauksessa käyttöliittymä pysäyttää animaation ja siirtää näkymän joko piste-ennätyksiin (mikäli pelaaja on pelin aikana kerännyt riittävästi pisteitä päästäkseen ennätyslistalle) tai takaisin päävalikkoon.


## Ohjelman rakenteeseen jääneet heikkoudet
### Käyttöliittymä
Käyttöliittymän luova luokka on varsin laaja ja sen metodit erittäin pitkiä. Eri näkymien luominen olisi mahdollisesti syytä erottaa erilliseen luokkaan tai luokkiin, tai sitten graafisen ilmeen luomisessa voitaisiin hyödyntää FXML:ää; joka tapauksessa graafisten elementtien luomiseen käytetyn koodin määrää olisi syytä karsia ja pitää luokassa mahdollisuuksien mukaan vain käyttöliittymän varsinainen toiminta.

Lisäksi käyttöliittymä olisi syytä erottaa vielä nykyistä paremmin sovelluslogiikasta erityisesti pelinäkymän aikana.

### Sovelluslogiikka
Tällä hetkellä luokka *SpriteHandler* joutuu ylläpitämään listoja siitä, minkä tyyppistä kappaletta mikäkin *Sprite* edustaa ja käsittelemään erityyppiset kappaleet esim. törmäysten yhteydessä erikseen (poikkeuksena ainoastaan pelaajan avaruusalus, jonka omaa luokkaa voidaan tietyissä tilanteissa kutsua). Varsinkin, jos sovelluksen jatkokehityksen yhteydessä *Sprite*-olioiden mahdollisten tyyppien määrä kasvaa nykyisestä, olisi luokka *Sprite* kenties syytä yleistää abstraktiksi luokaksi, luoda kullekin erityyppiselle kappaleelle omat luokkansa ja siirtää kappaleiden toimintaa erilaisissa tilanteissa kuvaava koodi *SpriteHandlerista* niihin.
