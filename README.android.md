# Folio for Android

The Android application shares Folio's React readers and adds native Android capabilities through Capacitor: SQLite persistence, the system file picker, app-private book storage, HTTPS downloads, libtorrent4j transfers, comic extraction, and a foreground download service. The phone interface uses bottom navigation and touch controls. Minimum Android version: 9 (API 28).

## Build

Use Android Studio's JDK 21 and Android SDK Platform 36. Set JAVA_HOME to the JDK and configure android/local.properties with the SDK path.

    npm install
    npm run android:build

The development APK is `android/app/build/outputs/apk/debug/app-debug.apk`. A debug APK is signed with the local Android development key. Do not delete that key if you want to install future updates over this build.

## Install with ADB

Enable USB debugging, connect the phone, and accept its debugging prompt.

    adb devices
    adb -s DEVICE_SERIAL install -r android/app/build/outputs/apk/debug/app-debug.apk
    adb -s DEVICE_SERIAL shell am start -n com.folio.reader/.MainActivity

`node tests/mobile-smoke.mjs DEVICE_SERIAL` tests the native bridge and OPDS source against the connected development build. It saves a screenshot under .qa.

## Book sources

In Add-ons, paste one of the following URLs and tap Install add-on:

- https://www.gutenberg.org/ebooks/search.opds/ — Gutenberg's official OPDS catalog. Search discovers titles and resolves EPUB editions.
- https://archive.org — Folio's built-in Internet Archive adapter. Searches text items and resolves available EPUB, PDF, comic, and torrent downloads. Restricted/borrow-only items are excluded from resolution.

Gutendex (https://gutendex.com/) is built into Discover and needs no installation. These are source connectors, not existing Stremio video add-ons. Folio v1 JSON manifests also remain supported. There is no established third-party ecosystem for this app's custom manifest format.

Standard Ebooks (https://standardebooks.org/) offers manually downloadable EPUBs for import. Its OPDS endpoints returned HTTP 401 during verification, so those feeds are not listed as ready-to-install sources.

## Mobile behavior

Imports preserve the original file. Books and reading progress stay on the phone; desktop libraries are separate. Incomplete downloads are restored as Paused after app restart and can be resumed. Downloads have a foreground notification while running; force-stopping Folio stops them. HTTP transfers finish automatically, while torrents have pause/resume controls. CBR uses Junrar and does not support RAR5 or encrypted archives. EPUB/PDF opening is capped at 128 MB and comic decoded data at 128 MB to limit mobile memory use.

## Verified on the connected phone

Installed and launched on Motorola Edge 60 Fusion (Android API 37, arm64). Production TypeScript/Vite build and Android debug APK compilation pass. Device checks verified SQLite persistence, a 434 CSS-pixel layout without overflow, Gutenberg OPDS installation/search/EPUB resolution, Internet Archive installation/search, EPUB download and opening, and successful native engine loading through magnet validation. A full torrent transfer and PDF/comic device fixtures remain unverified.

Gutenberg OPDS and Internet Archive were installed as enabled sources. The initial validation imported Alice's Adventures in Wonderland as a sample ebook.
