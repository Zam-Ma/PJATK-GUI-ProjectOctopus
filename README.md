# 🐙 Game & Watch: Octopus (Java Swing)

Niniejszy projekt stanowi implementację klasycznej gry zręcznościowej z serii *Nintendo Game & Watch*, zrealizowaną przy użyciu biblioteki Java Swing. Celem rozgrywki jest sterowanie postacią nurka wydobywającego skarby z dna oceanu, przy jednoczesnym unikaniu kolizji z mackami gigantycznej ośmiornicy. Aplikacja powstała w ramach przedmiotu Programowanie Interfejsów Graficznych (GUI). 🎓

![Rozgrywka w Octopus](https://github.com/Zam-Ma/PJATK-GUI-ProjectOctopus/blob/main/art_source/screenshot.png)

## 🎮 Mechanika Rozgrywki i Sterowanie

Rozgrywka rozpoczyna się z pulą trzech punktów życia. Podjęcie skarbu punktowane jest wartością 1 punktu, natomiast jego bezpieczny transport na łódź premiowany jest 3 punktami. Po przekroczeniu progu 200 oraz 500 punktów gracz otrzymuje dodatkowe życie. 💖

Sterowanie odbywa się za pośrednictwem wejść klawiaturowych:

* **`S`** – Inicjalizacja lub wznowienie wstrzymanej rozgrywki. ▶️

* **`A`** / **`←`** – Przemieszczenie postaci gracza w lewo (powrót na statek). ⬅️

* **`D`** / **`→`** – Przemieszczenie postaci gracza w prawo (kierunek dna oceanu). ➡️

## ✨ Kluczowe Funkcjonalności

* **Dedykowany silnik renderujący:** Wykorzystanie mechanizmu *Sprite Sheet* do precyzyjnego mapowania grafik 2D, z zachowaniem interpolacji *Nearest Neighbor* gwarantującej poprawne skalowanie struktury pikselowej. 🖼️

* **Wyświetlacz 7-segmentowy:** Dynamiczny system wizualizacji wyniku punktowego, konstruowany z wyselekcjonowanych fragmentów tekstur źródłowych. 📟

* **Adaptacyjny poziom trudności:** Szybkość aplikacji wzrasta proporcjonalnie do zdobytych punktów. Przekroczenie 999 punktów skutkuje tzw. *rolloverem* – celowym wyzerowaniem licznika i przywróceniem początkowego tempa rozgrywki. 📈

* **Mechanizm *Hitstop*:** Wstrzymanie renderowania animacji na jedną sekundę w chwili detekcji kolizji, co potęguje dynamikę zdarzeń i poprawia *game feel*. ⏱️

* **Syntetyzator sygnału dźwiękowego:** Zintegrowany generator dźwięku wykorzystujący interfejs **MIDI**, symulujący klasyczne, ośmiobitowe efekty całkowicie bezstratnie, bez użycia zewnętrznych plików audio. 🎵

## 🏗️ Architektura Systemu i Wzorce Projektowe

System zaprojektowano zgodnie z rygorystycznymi paradygmatami programowania obiektowego:

* **Separacja logiki od prezentacji:** Warstwa wizualna (`GamePanel`) odświeżana jest z częstotliwością bliską 60 FPS (`javax.swing.Timer`), podczas gdy logika wewnętrzna operuje w wyizolowanym środowisku wywołań. 🔄

* **Wielowątkowość (Multithreading):** Główna pętla zdarzeń i mechanika detekcji przeszkód zarządzane są przez niezależny wątek systemowy (`Thread`). 🧵

* **Wzorzec Singleton:** Klasa nadzorująca pętlę operacyjną (`GameThread`) została zabezpieczona wzorcem Singleton, uniemożliwiając incydentalne powielenie procesów logicznych. 🔒

* **Delegacyjny Model Zdarzeń:** Bezpieczna transmisja informacji pomiędzy wątkiem logicznym a komponentami planszy następuje poprzez dedykowany system zdarzeń (`TickEvent` oraz `TickListener`). 📨

## 🚀 Instrukcja Uruchomienia

Projekt opiera się wyłącznie na standardowych bibliotekach środowiska Java (wymagane JDK 8 lub nowsze) i nie wymaga zewnętrznych zależności. ☕

### Wariant A: Kompilacja z kodu źródłowego

1. Sklonuj repozytorium: `git clone <adres-repozytorium>`. 📥

2. Zaimportuj projekt do wybranego środowiska IDE (np. IntelliJ IDEA, Eclipse). 💻

3. Zweryfikuj obecność pliku graficznego `spritesheet.png` w katalogu zasobów (*Resources Root*). 📁

4. Skompiluj i uruchom główną klasę: `projekt2526.game.Main`. ▶️

### Wariant B: Uruchomienie archiwum .jar

1. Zlokalizuj skompilowany plik `.jar` w sekcji *Releases* repozytorium GitHub. 📦

2. Zainicjuj wykonanie pliku poprzez interfejs systemu lub wpisując w terminalu:

   ```bash
   java -jar projekt2526-octopus.jar


## ℹ️ Informacje Dodatkowe
Oprogramowanie zostało wytworzone jako realizacja wymogów zaliczeniowych przedmiotu Projekt GUI. 📚

Wykorzystane w kodzie zasoby graficzne mają charakter edukacyjny i stanowią wolną adaptację elementów z historycznej gry wydanej przez Nintendo Game & Watch: Octopus (1981). 🐙
