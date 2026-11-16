# AmongUsBase Offline Lab

Android-Demo mit lokalen Schaltern für Speed, Full Light, Long-Kill-Radius,
NoClip, Impostor-Demo-Rolle und Tracer-Linien.

**Abgrenzung:** Alle Funktionen wirken ausschließlich in der eingebauten
Demo-Arena. Die App liest oder verändert Among Us nicht, injiziert keinen
Code und benötigt keine Netzwerk- oder Sonderberechtigungen.

## Build in Android Studio
1. Android Studio installieren und starten.
2. **Open** wählen und den entpackten Ordner `AmongUsBase-Offline` öffnen.
3. JDK 17 auswählen (Android Studio: Settings > Build Tools > Gradle > Gradle JDK).
4. Android SDK Platform 35 installieren, falls Android Studio danach fragt.
5. Auf **Sync Project with Gradle Files** warten.
6. **Build > Build Bundle(s) / APK(s) > Build APK(s)** wählen.
7. Das Debug-APK liegt danach unter `app/build/outputs/apk/debug/app-debug.apk`.

## Build über GitHub Actions
Repository nach GitHub hochladen. Unter **Actions** den Workflow
**Android build** starten. Das erzeugte Debug-APK wird als Workflow-Artefakt
`AmongUsBase-Offline-debug` bereitgestellt.

## Prüfstatus
Quelltext- und Manifest-Prüfungen wurden durchgeführt. Ein echter Gradle-
Android-Build und ein Gerätetest konnten in der aktuellen Ausführungsumgebung
nicht stattfinden, weil weder Android SDK noch Gradle installiert sind und
der Download der Build-Werkzeuge nicht erreichbar war. Daher wird kein
kompiliertes APK als getestet oder garantiert lauffähig ausgegeben.
