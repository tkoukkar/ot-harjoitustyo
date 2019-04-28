# Murikat

Sovellus on Asteroids-tyyppinen peli. Pelaaja ohjaa avaruusalusta pelialueella, jolle ilmaantuu sen laidoilta ajoittain erilaisia asteroideja, vihollisaluksia ja bonusartefakteja. Näistä viimeksi mainittuja pelaajan tulee kerätä ja edellisiä puolestaan väistellä ja/tai tuhota.

## Dokumentaatio

[Käyttöohje](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely (alustava)](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus (alustava)](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Releaset

[Viikko 5](https://github.com/tkoukkar/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/tkoukkar/ot-harjoitustyo/releases/tag/viikko6)

## Komentorivikomennot

### Testaus

Testit suoritetaan komennolla:

`mvn test`

Mikäli testeistä halutaan luoda kattavuusraportti, komento tulee antaa muodossa:

`mvn test jacoco:report`

Kattavuusraportti muodostuu tiedostoon *target/site/jacoco/index.html*, jonka voi avata selaimella.

### Suoritettavan jarin generointi

Suoritettava jar-tiedosto generoidaan komennolla:

`mvn package`

Tiedosto generoidaan hakemistoon *target* nimellä *Murikat-1.0-SNAPSHOT.jar*.

### Checkstyle

Tiedoston [checkstyle.xml](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/Murikat/checkstyle.xml) määrittelemät tarkastukset suoritetaan komennolla:

`mvn test jxr:jxr checkstyle:checkstyle`

Mahdolliset virheilmoitukset generoidaan tiedostoon *target/site/checkstyle.html*, jonka voi avata selaimella.
