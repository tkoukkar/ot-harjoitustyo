# Vaatimusmäärittely

## Sovelluksen tarkoitus

Kyseessä on Asteroids-tyyppinen peli. Pelaaja ohjaa avaruusalusta pelialueella, jolle ilmaantuu sen laidoilta ajoittain erilaisia asteroideja, vihollisaluksia ja bonusartefakteja. Näistä viimeksi mainittuja pelaajan tulee kerätä ja edellisiä puolestaan väistellä ja/tai tuhota.

## Perusversion tarjoama toiminnallisuus

### Ennen pelin alkua

* Käyttäjä voi aloittaa uuden pelin.
* Käyttäjä voi tarkastella piste-ennätyslistaa.
* Käyttäjä voi sulkea ohjelman aloittamatta peliä.

### Pelin aikana

* Pelaaja voi ohjata avaruusalusta eri suuntiin.
* Avaruusaluksella on ase, jolla voi ampua asteroideja ja vihollisaluksia.
* Avaruusaluksella on alussa tietty määrä suojia. Suojat vähenevät asteroidin tai vihollisaluksen osumasta, ja niiden loputtua seuraava osuma tuhoaa aluksen, jolloin peli päättyy.
* Pelissä on pistelaskuri. Pelaaja saa pisteitä tuhoamalla asteroideja ja vihollisaluksia sekä keräämällä bonuksia.

### Pelin päätyttyä
* Pelaajan nimi ja pelin aikana kerätyt pisteet voidaan tallentaa piste-ennätyslistaan, jos niitä on riittävästi.
* Ohjelma palautuu yllä kuvattuun aloitustilaan.

## Jatkokehitysideoita

Sovelluksesta pyritään tekemään mahdollisimman helposti muokattava ja laajennettava. Mahdollisimman paljon alusten, vihollisten ym. objektien ominaisuuksia olisi tarkoitus kirjoittaa varsinaisen ohjelmakoodin sijasta ulkoiseen tietokantaan tai tietokantoihin, jotta niiden lisäys ja muokkaaminen käy helpommin. Mahdollisia tulevien versioiden ominaisuuksia ovat ajan salliessa mm. seuraavat:

* Pelin jakaminen tasoihin, jotka vaikeutuvat esim. uudentyyppisten vihollisalusten myötä asteittain
* Pelitilanteen tallennusmahdollisuus ainakin tasojen välissä
* Mahdollisuus kerätä pisteiden lisäksi tai sijasta rahaa, jolla avaruusalukseen voi ostaa (esim. erillisessä tasojen välisessä näkymässä) lisää suojia, parempia aseita ym.
