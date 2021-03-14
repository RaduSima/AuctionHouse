Sima Radu
321 CB

- README -

    Aplicatia functioneaza pe baza comenzilor de la tastatura. Aceasta are doua
moduri de input: comenzi din fisier sau comenzi de la tastatura, in functie
de numarul de parametri pasati mainului. Daca acesta nu primeste 3 parametri,
atunci comenzile se vor citi de la tastaura, iar outputul si erorile se vor
afisa la consola. Daca, in schimb, mainul primeste 3 parametri, atunci
acestia trebuie sa fie, in ordine: filepath catre fisierul de input,
filepath catre fisierul de output si filepath catre fisierul de erori, iar
inputul si outputul se vor citi/afisa corespunzator.
    Atat licitatiile, cat si comenzile, sunt procesate pe threaduri diferite, in
acelasi timp. Acest lucru se realizeaza prin extinderea interfetei Runnable de
catre clasele Administrator (pentru comenzi) si ActionHouse (pentru licitatii)
si prin utilizarea metodelor si colectiilor sincronizate.
    Cele 4 desgin patternuri utilizate sunt:
        - Singleton (pentru clasele AuctionHouse, ProductFactory si
        ClientFactory);
        - Factory (pentru clasele ProductFactory si ClientFactory);
        - Command (pentru clasa Command si subclasele sale);
        - Strategy (pentru clasa BiddingStrategy si subclasele sale).
    Genericitatea fost folosita atat prin utilizarea colectiilor, dar si prin
intermediul clasei Pair, care este una generica.
