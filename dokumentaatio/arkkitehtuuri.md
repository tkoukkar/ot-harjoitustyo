# Arkkitehtuurikuvaus

## Rakenne

Ohjelmakoodi on jaettu kolmeen pakkaukseen: *murikat.gui, murikat.logics* ja *murikat.dao*. Näistä *murikat.gui* sisältää käyttöliittymän, *murikat.logics* sovelluslogiikan ja *murikat.dao* datan hakemisesta ja tallentamisesta ulkoisiin tiedostoihin vastaavat luokat.

### Alustava pakkauskaavio

![alustava pakkauskaavio](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/pakkauskaavio.jpg)

## Sovelluslogiikka

Pelin aikana animaatiota pyörittää käyttöliittymän sisältämä *Timeline*-olio, joka kutsuu joka ruudunpäivityksen aikana sovelluslogiikan luokkien tarvittavia metodeja.

*InputHandler*-luokan ainoa olio käsittelee pelaajan antamat näppäinkomennot ja kutsuu niiden mukaan pelaajan avaruusalusta kuvaavan *Spaceship*-olion käännös- tai kiihdytysmetodeja. Lisäksi *InputHandler* merkitsee tarvittaessa liipaisimen painetuksi tai vapautetuksi, minkä perusteella muut luokat ohjaavat aluksen aseen toimintaa.

Kutakin pelin liikkuvista kappaleista kuvaa luokkaan *Sprite* kuuluva olio. Luokka sisältää muuttujat, joiden avulla pidetään kirjaa kunkin *Sprite*-olion sijainnista, orientaatiosta, liikesuunnasta ja -nopeudesta sekä siitä, onko kappale yhä pelissä vai tuhoutunut. Luokan metodit ovat pääosin gettereitä ja settereitä ja vastaavasti mahdollistavat kappaleen sijainnin ja liikkeen ym. ominaisuuksien muuttamisen.

Pelaajan avaruusalusta kuvaa lisäksi luokan *Spaceship* ainoa olio, joka pääosin välittää tietyin parametrein kutsuja vastaavalle *Sprite*-oliolle.

Pelin keskeisestä toiminnasta vastaa luokan *SpriteHandler* ainoa olio, joka pitää kirjaa pelissä olevista *Sprite*-olioista ja kutsuu niiden metodeja pelitilanteen mukaan. Joka ruudunpäivityksen yhteydessä käyttöliittymä kutsuu *SpriteHandler*in metodeja *processMovement(), processCollisions()* ja *processDestruction()*. Näistä *processMovement()* käy yksitellen läpi kaikki pelissä olevat *Sprite*t selvittäen niiden nykyisen sijainnin ja liikkeen sekä määrittäen niille uuden näiden perusteella uuden sijainnin. *processCollisions()* tutkii, onko kaksi eri *Sprite*-oliota liikkeen seurauksena törmännyt toisiinsa ja merkitsee osuman saaneet *Sprite*t tarpeen mukaan tuhotuiksi. *processDestruction()* vastaavasti poistaa tuhotut *Sprite*-oliot pelistä.

*SpriteHandler*-luokan *processCollisions()* metodi myös palauttaa käyttöliittymälle arvon *true* tai *false* sen mukaan, onko törmäys tapahtunut. Mikäli törmäys on tapahtunut, käyttöliittymä joko päättää pelin tai lisää pelaajalle pisteitä riippuen siitä, onko törmäyksessä tuhoutunut *Sprite* pelaajan alus vai jokin murikoista.

### Sekvenssikaavio tilanteesta, jossa pelaaja lähtee liikkeelle pelin alussa

![sekvenssikaavio](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio_murikat_viikko5.png)
