# Gooseletters 🦢📰

Gooseletters is a modern, high-fidelity Android news reading application designed with a classic, editorial print-journalism aesthetic inspired by traditional newspapers. Powered by **The Guardian API**, it delivers a beautifully formatted feed of global news with clean typography, elegant outlines, and a spacious reading layout.

---

## ✨ Features & Highlights

### 🔄 Seamless Pull-to-Refresh
- **Gesture-Based Reloading**: The old clunky manual reload button has been replaced. You can now pull/scroll down on the news feed to trigger a smooth, standard Material 3 pull-to-refresh action, seamlessly fetching the latest news.

### 🌓 Live Light & Dark Mode
- **Dual Visual Theme support**: Experience comfortable reading day or night. Switch between a crisp print-paper light theme and a rich, eye-soothing dark theme.
- **Theme Controls**: 
  - An instant **quick-toggle button** is available directly in the feed's top header bar.
  - Detailed controls are placed in the **App Customization** dialog, allowing you to force Light, force Dark, or fall back to the **System Default** automatically.

### 🏷️ Permanent Custom Branding
- **Permanent Editorial Logo**: Gooseletters displays a professionally styled permanent custom application logo (`app_logo.jpg`) centered at the top of both the **Main Feed** and the **Article Details Screen** for cohesive and polished brand identity.

### 🌐 Real-Time News Delivery
- **Sections & Categories**: Easy horizontal tab switching between Home (all news), Technology, World News, Business, Science, and Politics.
- **Inline Search**: High-performance keyword search querying live Guardian API servers.
- **Offline Resiliency**: In-memory state holding and beautiful error handling to keep reading smooth.

---

## 🏗️ Architecture & Package Structure

The project strictly follows modern Android development guidelines and an **MVVM (Model-View-ViewModel)** clean architecture layered structure:

```
com.example/
├── MainActivity.kt               # App entry point, handles Edge-to-Edge and Theme Mode
├── data/
│   ├── api/
│   │   └── GuardianApiService.kt # Retrofit interface for HTTP queries
│   ├── model/
│   │   └── GuardianResponse.kt   # Gson response DTO mapping schemas
│   └── repository/
│       └── NewsRepository.kt     # Single source of truth for loading feed/search results
└── ui/
    ├── screens/
    │   ├── MainFeedScreen.kt     # Main screen with Pull-to-Refresh, Theme switch, and Categories
    │   └── ArticleDetailScreen.kt# Full-screen editorial article viewer
    ├── theme/
    │   ├── Color.kt              # Custom warm editorial print palette
    │   ├── Theme.kt              # Material 3 dynamic color scheme adapter
    │   └── Type.kt               # NYT-style Serif & Sans-Serif typography pairing
    └── viewmodel/
        └── NewsViewModel.kt      # State engine holding Theme preferences, API Keys, and News streams
```

---

## 🛠️ Technology Stack

- **UI Framework**: [Jetpack Compose](https://developer.android.com/compose) (100% Declarative UI)
- **Design System**: [Material Design 3 (M3)](https://m3.material.io/) with edge-to-edge layout handling
- **Asynchronous / Stream Processing**: Kotlin Coroutines & StateFlow
- **Networking**: Retrofit & OKHttp
- **JSON Parsing**: Gson Converter
- **Image Loading**: Coil (via AsyncImage) for dynamic imagery loading
- **Local Persistence**: SharedPreferences for saving local API keys and theme configurations

---

## 🚀 Getting Started

### 🔑 Setting up your Guardian API Key
By default, the app is configured with a public `"test"` developer key, which grants access to main article feeds but may limit some features.

To use your own personalized access:
1. Tap on the **Settings** icon (or the **GOOSELETTERS** branding header) at the top of the feed.
2. Enter your custom **The Guardian API key** in the input field.
3. Tap **SAVE**. Your key will be securely saved locally across app restarts.

---

## 🎨 Design Philosophy
Gooseletters bridges the gap between old-world print journalism and high-performance digital apps.
- **Typography Pairing**: Elegant Georgia serif headers paired with clean Sans-serif body copy.
- **Negative Space**: Generous padding around layout boundaries prevents content from feeling crammed, matching the layout principles of classic Sunday broadsheets.
- **High-contrast**: Crisp borders and paper-colored background cards anchor the articles in modern, responsive blocks.
