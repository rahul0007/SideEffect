This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop, Server.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/server` is for the Ktor server application.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.

---

# 📝 What are Side-Effects in Compose?

A side effect is any action in Compose that happens outside the normal UI rendering, such as showing a toast, launching a coroutine, or reading from shared preferences.

---


## 🛠 Types of Side Effects

| Effect                     | When It Runs                                      | Common Use Case                     |
|----------------------------|----------------------------------------------------|---------------------------------------|
| **LaunchedEffect**         | On key change or initial composition              | Start coroutine tied to lifecycle    |
| **SideEffect**             | After every successful recomposition & commit     | Sync external state (e.g., analytics)|
| **DisposableEffect**       | On enter/exit of the Composable                   | Setup & cleanup resources            |
| **rememberCoroutineScope** | Coroutine scope for user-triggered actions        | Button click APIs, animations        |
| **rememberUpdatedState**   | Always gives latest value in long-lived effects   | Avoid stale callbacks                |

---

## 📜 Example Logs
When you click the button:

🟢 SideEffect: After recomposition. Count=1
🟣 LaunchedEffect: Count=1 (starting work)
🔵 rememberCoroutineScope: Coroutine started for count=1
🟣 LaunchedEffect: Count=1 (work done)
✅ rememberUpdatedState: Handling click #1

---


When you navigate away:

🟠 DisposableEffect: Cleaned up resource



---

