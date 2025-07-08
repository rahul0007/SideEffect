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
| 🛠️ Effect                   | 🕒 Trigger                   | 🌱 Auto Cancel | 🎯 Purpose                                   |
| ---------------------------- | ---------------------------- | --------------- | ---------------------------------------------|
| `LaunchedEffect`             | On enter/key change          | ✅ Yes          | Start coroutines tied to Composition         |
| `SideEffect`                 | After every recomposition    | ✅ Yes          | Non-suspending sync work after recomposition |
| `DisposableEffect`           | On enter/leave or key change | ✅ Yes          | Manage lifecycle resources                   |
| `produceState`               | On enter/key change          | ✅ Yes          | Async state production                       |
| `rememberCoroutineScope`     | Once per Composition         | ✅ Yes          | User-triggered coroutines                    |

---

### ✅ 1. `LaunchedEffect`

#### 📖 **Definition**

- Launches a coroutine that is **tied to the Composition** lifecycle.
- Automatically cancels and restarts if the key changes.
- Ideal for **starting asynchronous work** when a Composable enters composition.

#### 🗓 **When it runs**

- When the Composable first enters Composition.
- When the provided **key(s)** change.

#### 📝 **Example**

```kotlin
LaunchedEffect(userId) {
    val user = repository.fetchUser(userId)
    println("Fetched user: $user")
}
```

✅ 2. SideEffect
📖 Definition
Runs after every successful recomposition.

Used for non-suspending logic that needs to sync with external code.

🗓 When it runs
Every time Compose recomposes.

📝 Example

```
📌 Best Practices
✅ Use LaunchedEffect for one-time coroutines like API calls.
✅ Use SideEffect sparingly; avoid doing heavy work there.
✅ Use DisposableEffect to manage resources properly.
✅ Avoid launching infinite loops in rememberCoroutineScope – prefer LaunchedEffect.
✅ Combine rememberUpdatedState with LaunchedEffect for closures.
```
```text
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

