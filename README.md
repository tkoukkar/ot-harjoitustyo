# Murikat

Sovellus on Asteroids-tyyppinen peli. Pelaaja ohjaa avaruusalusta pelialueella, jolle ilmaantuu sen laidoilta ajoittain erilaisia asteroideja, vihollisaluksia ja bonusartefakteja. Näistä viimeksi mainittuja pelaajan tulee kerätä ja edellisiä puolestaan väistellä ja/tai tuhota.

## Dokumentaatio

[Käyttöohje](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely (alustava)](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus (alustava)](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Releaset

[Viikko 5](https://github.com/tkoukkar/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivikomennot

### Testaus

Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn test jacoco:report`

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto *target/site/jacoco/index.html*.

### Suoritettavan jarin generointi

Komento

`mvn package`

generoi hakemistoon *target* suoritettavan jar-tiedoston *Murikat-1.0-SNAPSHOT.jar*.

### Checkstyle

Tiedoston [checkstyle.xml](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/Murikat/checkstyle.xml) määrittelemät tarkastukset suoritetaan komennolla

`mvn jxr:jxr checkstyle:checkstyle`

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto *target/site/checkstyle.html*
