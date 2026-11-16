# Build- und Sicherheitsstatus

## Statische Prüfungen
- AndroidManifest.xml ist valides XML.
- Java-Klammerstruktur ist ausgeglichen.
- Alle sechs Offline-Demo-Schalter sind an den Demo-Zustand gebunden.
- Keine INTERNET-Berechtigung deklariert.
- Keine Dobby-, IL2CPP-, JNI- oder Prozessinjektions-Aufrufe.
- Keine Runtime.exec-, ProcessBuilder-, DexClassLoader- oder Accessibility-Service-Aufrufe.
- Keine nativen Bibliotheken im Projekt.

## Nicht durchgeführt
- Gradle `assembleDebug`
- Android Lint
- Installation / Laufzeittest auf echtem Android-Gerät
- APK-Signatur- und Malware-Scan

Grund: In der Arbeitsumgebung sind Android SDK und Gradle nicht installiert;
Netzwerkzugriff zum Abruf der Build-Werkzeuge ist nicht verfügbar.

## Ergebnis
Quellcode-Checks bestanden. Das ist keine APK-Freigabe und keine Garantie
für Fehlerfreiheit. Build und Gerätetest müssen in Android Studio oder
GitHub Actions abgeschlossen werden.
