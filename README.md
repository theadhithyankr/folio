# Folio

**A quiet home for every story.**

Folio is a local-first ebook reader and library manager for Windows and Android. It brings your EPUBs, PDFs, and comic archives together in one calm, Stremio-inspired library — and adds built-in catalogs (Project Gutenberg, Internet Archive) plus an extensible add-on system for discovering and downloading books directly inside the app.

| Platform | Status | Install |
| --- | --- | --- |
| Windows 10/11 (x64) | ✅ Supported | [Portable zip](https://github.com/theadhithyankr/folio/releases) — extract and run `Folio.exe` |
| Android 9+ (API 28) | ✅ Supported | [APK](https://github.com/theadhithyankr/folio/releases) |
| Browser preview | ✅ Catalog browsing only | `npm run dev` |

## The problem Folio solves

Reading apps tend to force a trade-off: either they lock you into a store and a cloud account, or they are bare containers that don't help you *find* anything. Folio is built around three ideas:

1. **One library for every format.** EPUB, PDF, CBZ, and CBR live under a single shelf with collections, filters, covers, and resume-where-you-left-off progress — no per-format apps, no cloud sync required.
2. **Discovery without friction.** Search public-domain classics from Project Gutenberg (built-in via Gutendex), install the Gutenberg OPDS feed or the Internet Archive adapter, and download straight into your library — including magnet links and torrents — with no external torrent client.
3. **Your data stays yours.** Books, reading progress, bookmarks, and settings are stored locally in a private SQLite database. Imports copy files while preserving your originals. No account, no telemetry, no cloud account required.

## What's inside

- **Featured readers** — EPUB (epub.js), PDF (PDF.js with text search and zoom), CBZ/CBR comics (single page, spread, vertical scroll, right-to-left support).
- **Customizable reading** — light / sepia / dark themes, font size, line spacing, margins, table of contents, bookmarks, and fullscreen.
- **Library management** — filters (Reading, Finished, format), sorting, import from disk, and library removal that never touches your originals unless you explicitly choose to delete the managed copy.
- **Downloads manager** — HTTP and BitTorrent transfers with progress, speed, and peer info; torrent file selection; pause/resume for torrents; a foreground notification service on Android.
- **Add-on system** — install a Folio v1 JSON manifest or any OPDS feed by URL. Enabled sources show up in searches automatically. See the [add-on protocol](#add-on-protocol).

## Getting started

### Windows desktop

Requires Node.js 24 LTS.

```bash
git clone https://github.com/theadhithyankr/folio.git
cd folio
npm install
npm run desktop
```

Package a Windows installer and portable build (unsigned):

```bash
npm run pack
```

### Android

Requires Android Studio with JDK 21 and SDK Platform 36. Full instructions — build, ADB install, and device verification notes — are in [README.android.md](README.android.md).

```bash
npm install
npm run android:build
```

Or download the current debug APK directly from the [Releases](https://github.com/theadhithyankr/folio/releases) page. It is signed with a local development key (Android 9+ / arm64).

### Sources you can install immediately

In **Add-ons**, paste one of these and tap *Install add-on*:

- `https://www.gutenberg.org/ebooks/search.opds/` — Gutenberg's official OPDS catalog
- `https://archive.org` — built-in Internet Archive adapter (EPUB, PDF, comics, torrents; borrow-only items excluded)

Gutendex powers the built-in **Discover** shelf and needs no installation.

## Add-on protocol

Install an HTTPS URL serving this manifest:

```json
{"version":1,"id":"example","name":"Example Books","description":"My catalog","baseUrl":"https://example.com/folio"}
```

Search endpoint — `GET /search?query=...`:

```json
{"books":[{"id":"123","title":"A Book","author":"An Author","cover":"https://example.com/cover.jpg","description":"About this edition"}]}
```

Resolve endpoint — `GET /sources?id=123`:

```json
{"sources":[{"name":"Example source","format":"EPUB","url":"magnet:?xt=urn:btih:..."}]}
```

Sources can also be supplied inline in search results. Supported formats are EPUB, PDF, CBZ, and CBR; URLs may be HTTPS files, HTTPS `.torrent` files, or magnet links. Providers run on their own servers — Folio only loads JSON. Stremio video add-ons are not compatible. A local development fixture lives at `examples/provider.mjs`.

## Storage architecture

- **Desktop:** SQLite and managed books live in the Electron `userData` directory (`%APPDATA%/Folio`). Imported originals are never modified.
- **Android:** books and progress persist in app-private storage via Capacitor SQLite; desktop and mobile libraries are independent. Interrupted downloads are restored as *Paused* after app restart.

## Testing

```bash
npm test          # unit tests: extensions, archive traversal, name normalization, comic ordering
npm run smoke     # desktop smoke: renderer startup, SQLite IPC, torrent utility process
node tests/mobile-smoke.mjs DEVICE_SERIAL   # native bridge + OPDS against a connected device
```

TypeScript compilation and the production Vite build pass. Live verification included the Gutendex catalog, Gutenberg OPDS search and EPUB resolution, and Internet Archive search on a real device (Motorola Edge 60 Fusion).

## Known limitations

- Scanned PDFs and comics have no OCR; text search only works on extracted text.
- DRM-protected books, encrypted/password archives, and RAR5 comics (CBR) are unsupported.
- EPUB/PDF/comic decode is capped (~128 MB on mobile) to protect memory; very large comics are best read on desktop.
- Builds are unsigned; the shipped APK is a development (debug) build.
- Full torrent end-to-end transfer and PDF/comic device fixtures remain unverified.

## Privacy

All data — books, progress, bookmarks, settings, add-ons — stays on your device. Torrent transfers run locally and stop on completion; downloaded files remain available offline. Nothing is uploaded anywhere.

---

*Folio · Made for getting lost in a book.*
