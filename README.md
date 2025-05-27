# NutriTrack 🍽️📱

NutriTrack is an Android app built using Kotlin and Jetpack Compose for managing and monitoring patient nutritional intake. The app features secure authentication, AI-driven health insights, and comprehensive data tracking.

## 🌟 Features

- 🔐 **Authentication**
  - **Login** and **Register** pages with encrypted password storage
  - Patients claim accounts using `UserID` and `PhoneNumber`
  - Persistent login until manual logout

- 🤖 **AI Integration**
  - Integrates with **Fruity Vice API** for detailed nutritional data on fruits
  - Uses **Gemini API** to provide AI-driven personalized health insights based on user data

- 📊 **Food Intake Questionnaire**
  - Patients record dietary intake through an interactive questionnaire
  - Data linked to patient records using Room database foreign keys

- 💾 **Room Database**
  - Patient data is auto-loaded from CSV on first app launch
  - Efficient local data storage with no repeated CSV imports

- 🎨 **Modern UI**
  - Built entirely with Jetpack Compose for a clean, responsive user experience

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Database:** Room
- **Architecture:** MVVM
- **APIs:** Fruity Vice API, Gemini API for AI health analytics

## 🔒 Security

- Passwords are hashed before storage
- Secure token management for persistent login

## 🚀 Getting Started

1. Clone the repo:
   ```bash
   git clone https://github.com/yourusername/NutriTrack.git
2. Open the project in Android Studio.
3. Run on emulator or physical device.
4. On first launch, patient data will be imported automatically from CSV.

## 📁 Folder Structure
NutriTrack/
├── app/
│   ├── src/
│   │   ├── main/java/...
│   │   └── res/
│   ├── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── .gitignore

Developed as part of Monash University Malaysia coursework in Android development (FIT2081).
