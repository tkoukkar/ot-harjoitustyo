# Vaatimusmäärittely

## Sovelluksen tarkoitus

Kyseessä on Asteroids-tyyppinen peli. Pelaajan tehtävänä on ohjata avaruusalusta pelialueella, jolle ilmaantuu sen laidoilta ajoittain asteroideja eli *murikoita*. Tavoitteena on tuhota murikoita ampumalla niitä aluksen aseella sekä välttää niihin osumista. Murikoiden tuhoamisesta saa pisteitä, kun taas niihin törmääminen heikentää aluksen suojia ja lopulta tuhoaa itse aluksen. Pelaajan aluksen tuhoutuessa peli päättyy.

## Perusversion tarjoama toiminnallisuus

### Ennen pelin alkua

* Käyttäjä voi aloittaa uuden pelin.
* Käyttäjä voi tarkastella piste-ennätyslistaa.
* Käyttäjä voi sulkea ohjelman aloittamatta peliä.

### Pelin aikana

* Pelaaja voi ohjata avaruusalusta eri suuntiin.
* Avaruusaluksella on ase, jolla voi ampua murikoita.
* Avaruusaluksella on alussa kolme suojaa. Suojat vähenevät yhdellä aina aluksen törmätessä murikkaan; viimeisen suojan tuhoutuessa tuhoutuu myös alus itse, jolloin peli päättyy.
* Pelissä on pistelaskuri. Pelaaja saa pisteitä tuhoamalla asteroideja ja vihollisaluksia sekä keräämällä bonuksia.

### Pelin päätyttyä
* Ohjelma siirtyy pelaajan keräämien pisteiden riittäessä piste-ennätysnäkymään, jossa on mahdollista tallentaa pelaajan nimi ja pisteet piste-ennätyslistaan.
* Ohjelma palautuu (tai on piste-ennätysnäkymästä palautettavissa) yllä kuvattuun aloitustilaan.

## Jatkokehitysideoita

Mahdollisia tulevien versioiden ominaisuuksia ovat mm. seuraavat:

* Murikoiden lisäksi pelialueelle ilmestyvät vihollisalukset 
* Pisteitä tai aluksen ominaisuuksia lisäävät bonusartefaktit
* Pelin jakaminen tasoihin, jotka vaikeutuvat esim. uudentyyppisten vihollisalusten myötä asteittain
* Pelitilanteen tallennusmahdollisuus ainakin tasojen välissä
* Mahdollisuus kerätä pisteiden lisäksi tai sijasta rahaa, jolla avaruusalukseen voi ostaa (esim. erillisessä tasojen välisessä näkymässä) lisää suojia, parempia aseita ym.
* Pelaajan kulloisellakin pelikerralla keräämien pisteiden korostus piste-ennätysnäkymässä
