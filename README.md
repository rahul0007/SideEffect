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

### 📝 What is LaunchedEffect?
- LaunchedEffect is a side-effect API in Compose.
- It launches a coroutine tied to the composition lifecycle.
- It runs when keys change or on initial composition, and it cancels automatically if the composable leaves the composition.
- Automatically cancels and restarts if the key changes.
- Ideal for **starting asynchronous work** when a Composable enters composition.

#### 🗓 **When it runs**

- When the Composable first enters Composition.
- When the provided **key(s)** change.

#### 📝 **Example**

```kotlin
@Composable
fun CounterExample() {
    var counter by remember { mutableStateOf(0) }

    // Runs every time counter changes
    LaunchedEffect(counter) {
        println("LaunchedEffect: Counter changed to $counter")
        delay(1000)
        println("Finished delay for counter=$counter")
    }

    Column {
        Text("Counter: $counter")
        Button(onClick = { counter++ }) {
            Text("Increment Counter")
        }
    }
}
```
- Every time you click Increment Counter, LaunchedEffect cancels the previous coroutine and starts a new one because the counter key changed.

  #### 🔥 What happens if key changes?
  If the key changes:
  1) Cancel the old coroutine.
  2) Start a new coroutine.

| Feature                        | LaunchedEffect  | rememberCoroutineScope       |
| ------------------------------ | --------------- | ---------------------------- |
| Tied to composition            | ✅ Yes          | ❌ No                       |
| Cancels when composable exits  | ✅ Yes          | ❌ No (you cancel manually) |
| Starts coroutine automatically | ✅ Yes          | ❌ You call launch manually |
| Key-based restart              | ✅ Yes          | ❌ No                       |

  
####  2.  What is SideEffect?


- SideEffect is a Compose effect that allows you to perform synchronous actions after every successful recomposition.

- Unlike LaunchedEffect, it does not launch a coroutine.

- It’s used for non-suspending, one-time actions like updating values in a remember block, or notifying external APIs.

📝 Example
```kotlin
@Composable
fun SideEffectExample() {
    var counter by remember { mutableStateOf(0) }

    // SideEffect runs after every recomposition
    SideEffect {
        println("🟣 SideEffect: Counter is $counter")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Counter: $counter", style = MaterialTheme.typography.titleLarge)
        Button(onClick = { counter++ }) {
            Text("Increment")
        }
    }
}
```
 - Every time you click Increment, the UI recomposes and SideEffect is called.

 #### 🧠 When to use SideEffect?

 ✅ When you need to:

- Notify external APIs or SDKs of a state change.

- Update values that are not part of Compose state.

- Call methods that require recomposition to finish.



#### 🟣 Difference: SideEffect vs LaunchedEffect

| Feature              | **SideEffect**            | **LaunchedEffect**                      |
| -------------------- | ------------------------- | --------------------------------------- |
| When it runs         | After every recomposition | When the **key changes** or composition |
| Can suspend?         | ❌ No                     | ✅ Yes                                 |
| Tied to coroutines?  | ❌ No                     | ✅ Yes (launches coroutine)            |
| Cancels on disposal? | N/A                       | ✅ Cancels coroutine on disposal        |
| Use case             | Synchronous side effects  | Asynchronous suspending tasks           |


#### 🔴 What is DisposableEffect?

- DisposableEffect is a side-effect API in Compose.

- It’s used when you need to set up and clean up resources based on the lifecycle of a composable.

- You provide keys (dependencies). When these keys change or the composable leaves the composition, cleanup happens automatically.

 #### Example

```kotlin
 @Composable
fun BroadcastReceiverExample(context: Context) {
    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                println("🔴 Broadcast received!")
            }
        }
        val filter = IntentFilter("MY_CUSTOM_ACTION")
        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
            println("⚫ Receiver unregistered")
        }
    }

    Text("Waiting for broadcast...")
}
```

### ✅ Key Use Cases

✔ Registering/unregistering listeners (like BroadcastReceiver, LifecycleObserver).
✔ Managing resources that need cleanup (camera, GPS, sensors).
✔ Observing external APIs that are not Compose-aware.

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

