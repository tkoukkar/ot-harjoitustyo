# Käyttöohje



## Konfigurointi

Ohjelma on käynnistettävä samasta hakemistosta, kuin missä sijaitsee sen konfiguraatiotiedosto config.properties; muutoin se ei löydä tarvitsemiaan datatiedostoja.

## Käynnistäminen

Ohjelma käynnistyy komentoriviltä komennolla `java -jar murikat.jar`.

## Päävalikko

Ohjelman käynnistyessä avautuu päävalikko, jossa valittavana on seuraavat vaihtoehdot:

**Uusi peli** - aloittaa uuden pelin

**Ennätykset** - näyttää listan parhaita tuloksia, jotka pelissä on saavutettu

**Lopeta** - sulkee ohjelman

## Pelin kulku

Peli alkaa, kun pelaaja napsauttaa päävalikossa Uusi peli -painiketta. Pelin alussa pelialueen keskellä näkyy valkoinen avaruusalus ja pelialueen laidoilla neljä harmaata murikkaa, jotka lähtevät liikkeelle satunnaisiin suuntiin. Pelaajan tehtävänä on ohjata avaruusalusta ja ampua sillä murikoita sekä välttää niihin osumista. Ruudun vasemmassa yläkulmassa näkyvät pelaajan keräämät pisteet ja näiden alapuolella aluksen suojia kuvaava palkki.

Kun pelaaja osuu aseella murikkaan, murikka halkeaa kahtia ja puolikkaat jatkavat liikettä eri suuntiin. Puolikkaat murikat käyttäytyvät samoin kuin alkuperäiset, mutta ovat pienempiä ja hieman nopeampia. Myös puolikkaat murikat halkeavat ammuttaessa, jolloin syntyy kaksi vielä pienempää murikkaa. Nämäkin käyttäytyvät muutoin samoin kuin isommat murikat, mutta tuhoutuvat ammuttaessa.

Myös uusia isoja murikoita ilmestyy satunnaisin väliajoin lisää pelialueen laidoille; todennäköisyys riippuu yhtäältä pelaajan keräämistä pisteistä ja toisaalta pelialueella kulloinkin jo olevien murikoiden määrästä.

Murikoiden ampumisesta saa pisteitä seuraavasti:

* iso murikka: 60 p
* puolikas: 120 p
* puolikkaan puolikas: 180 p

Avaruusaluksen törmätessä murikkaan aluksen suojat heikkenevät, mikä näkyy suojia kuvaavan palkin lyhenemisenä ja värinmuutoksena. Viimeisen suojan (palkissa enää yksi punainen neliö) tuhoutuessa tuhoutuu myös alus itse, jolloin peli päättyy.

## Näppäinkomennot

Avaruusalusta ohjataan näppäimistöllä. Näppäinkomennot ovat seuraavat:

**← (vasen nuolinäppäin)** - käänny aluksen keulan osoittamaan suuntaan nähden vasemmalle

**→ (oikea nuolinäppäin)** - käänny aluksen keulan osoittamaan suuntaan nähden oikealle

**↑ (ylös-nuolinäppäin)** - kiihdytä aluksen keulan osoittamaan suuntaan

**välilyönti** - ammu

## Piste-ennätykset

Mikäli pelaajalla on pelin päättyessä riittävästi pisteitä, pääsee hän tallentamaan nimensä piste-ennätyslistalle.
Tämä tapahtuu syöttämällä nimi tätä tarkoitusta varten automaattisesti avautuvaan tekstikenttään sekä napsauttamalla sen
vieressä olevaa OK-painiketta.

