# Bitcoin Ticker 📊

Bitcoin Ticker is a feature-rich mobile application built to provide real-time cryptocurrency market data, with functionalities for managing favorite coins, price alerts, and historical market data visualization. Designed with a clean architecture, the app leverages modern technologies and tools to ensure high performance, maintainability, and a smooth user experience.

---

## Features 🌟

### 1. **Real-Time Cryptocurrency Data**
   - Displays live prices and market data for Bitcoin and other cryptocurrencies.
   - Provides detailed information such as market capitalization, price changes, and historical data.

### 2. **Favorites Management**
   - Users can add coins to their favorites list.
   - Supports customizable price alert intervals for favorite coins.

### 3. **Price Alerts**
   - Sends notifications for price changes in favorite coins.
   - Allows users to set refresh intervals to stay updated with market trends.

### 4. **Interactive Charts**
   - Visualizes historical market data using line charts.
   - Supports daily and hourly data views.

### 5. **User Authentication**
   - Secure login using Firebase Authentication.
   - Features account creation, login, and password recovery.

### 6. **Analytics and Crash Reporting**
   - **Firebase Analytics**: Tracks user interactions for insights.
   - **Firebase Crashlytics**: Reports crashes for enhanced app stability.

---

## Architecture 🏗️

The app follows the **MVVM (Model-View-ViewModel)** architectural pattern with a focus on separation of concerns and testability.

- **Model**: Handles business logic and data (repositories and data sources).
- **ViewModel**: Exposes data streams for UI consumption and manages UI-related logic.
- **View**: Displays data and interacts with users.

---

## Technology Stack ⚙️

### 1. **Frontend**
   - **Jetpack Compose**: For building modern, declarative user interfaces.
   - **Material3 Components**: Ensures visually consistent design.

### 2. **Backend**
   - **Firebase Authentication**: Secures user authentication.
   - **Firestore**: Safely stores favorite coins and user preferences.

### 3. **Networking**
   - **Retrofit**: Manages API requests and responses.
   - **Gson**: Parses JSON responses.

### 4. **Concurrency**
   - **Kotlin Coroutines**: Manages asynchronous operations efficiently.
   - **Kotlin Flow**: Handles real-time data streams for live updates.

### 5. **Dependency Injection**
   - **Hilt**: Simplifies dependency injection and reduces boilerplate code.

### 6. **Work Scheduling**
   - **WorkManager**: Schedules background tasks for fetching price updates and sending notifications.

### 7. **Charts**
   - **MPAndroidChart**: Creates interactive and customizable market data charts.

### 8. **Notifications**
   - **Android NotificationManager**: Displays price change alerts to users.

### 9. **Analytics and Crash Reporting**
   - **Firebase Analytics**: Monitors user activity and engagement.
   - **Firebase Crashlytics**: Captures crash reports for debugging and stability.

---

## App Workflow 🛠️

### 1. **User Login**
   - Users authenticate securely via Firebase Authentication.
   - Personalized sessions are maintained.

### 2. **Fetching Coin Data**
   - Real-time data is fetched from the CoinGecko API using Retrofit.
   - Both live and historical data are supported.

### 3. **Favorites and Price Alerts**
   - Favorite coins are stored in Firestore.
   - Background workers (WorkManager) periodically check for price updates and send notifications.

### 4. **Interactive Charts**
   - Historical market data is visualized with MPAndroidChart.
   - Supports daily and hourly data views.

### 5. **Analytics and Crash Reporting**
   - User interactions are tracked via Firebase Analytics.
   - Crashes are monitored and reported using Firebase Crashlytics.

---

## Project Structure 📂

```
app/
├── data/
│   ├── cache/                 # Firestore-related data handling
│   ├── model/                 # Data models for API and Firestore
│   ├── repository/            # Repositories for data management
│   ├── work/                  # WorkManager tasks
├── di/                        # Dependency injection modules
├── ui/
│   ├── components/            # Reusable UI components
│   ├── navigation/            # Navigation setup with NavHostController
│   ├── screens/               # Individual screen implementations
├── utils/                     # Utility classes and helpers
├── viewmodel/                 # ViewModels for state and UI logic
```

---



