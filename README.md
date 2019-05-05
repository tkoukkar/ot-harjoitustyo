# Murikat

Kyseessä on Asteroids-tyyppinen peli. Pelaajan tehtävänä on ohjata avaruusalusta pelialueella, jolle ilmaantuu sen laidoilta ajoittain asteroideja eli murikoita. Tavoitteena on tuhota murikoita ampumalla niitä aluksen aseella sekä välttää niihin osumista.

## Dokumentaatio

[Käyttöohje](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)


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

Tiedosto generoituu hakemistoon *target* nimellä *Murikat-1.0-SNAPSHOT.jar*. Ohjelman suorittaminen vaatii lisäksi muodostuneen jar-tiedoston siirtämisen manuaalisesti projektin juurihakemistoon (vrt. [käyttöohje](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)), eli hakemistossa *target* on annettava komento:

`mv Murikat-1.0-SNAPSHOT.jar ../`

### Checkstyle

Tiedoston [checkstyle.xml](https://github.com/tkoukkar/ot-harjoitustyo/blob/master/Murikat/checkstyle.xml) määrittelemät tarkastukset suoritetaan komennolla:

`mvn test jxr:jxr checkstyle:checkstyle`

Mahdolliset virheilmoitukset generoidaan tiedostoon *target/site/checkstyle.html*, jonka voi avata selaimella.
