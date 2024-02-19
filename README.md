<h1 align="center">EasyDataStore</h1><br>

<p align="center">
	<a href="https://jitpack.io/#MikoPetryk/EasyDataStore/2.0.0-rc1"><img alt="jitpack" src="https://jitpack.io/v/MikoPetryk/EasyDataStore.svg"/></a>
	<a href="https://github.com/MikoPetryk"><img alt="github" src="https://img.shields.io/badge/Github-Miko%20Petryk-blue"/></a>
</p>


<p align="center">
EasyDataStore is an Android library that simplifies the usage of DataStore. 
With EasyDataStore, you can easily define, access, and modify your settings values using a single object.
</p>

## Download
[![](https://jitpack.io/v/MikoPetryk/EasyDataStore.svg)](https://jitpack.io/#MikoPetryk/EasyDataStore/2.0.0-rc1)<br>

### Gradle
Add a repository in your `settings.gradle` file:
```gradle
dependencyResolutionManagement {
    repositories {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        ...
        maven(url = "https://jitpack.io")
        //maven { setUrl("https://jitpack.io") }	// If using settings.gradle.kts
    }
}
```
Add the dependency below to your module's `build.gradle` file:
```gradle
dependencies {
    implementation 'com.github.MikoPetryk:EasyDataStore:$latest_version'
    //implementation ("com.github.MikoPetryk:EasyDataStore:$latest_version") // If using Gradle DSL
}
```
## Supported data types
### Boolean
```kotlin
val booleanExample = DataStoreValue(
    key = "boolean-example",
    default = true
)
```
### Integer
```kotlin
val intExample = DataStoreValue(
    key = "int-example",
    default = 1
)
```
### Float
```kotlin
val floatExample = DataStoreValue(
    key = "float-example",
    default = 1f
)
```
### Double
```kotlin
val doubleExample = DataStoreValue(
    key = "double-example",
    default = 1.0
)
```
### Long
```kotlin
val longExample = DataStoreValue(
    key = "long-example",
    default = 1L
)
```
### String
```kotlin
val stringExample = DataStoreValue(
    key = "string-example",
    default = "String"
)
```
### StringSet
When reading `StringSet` returns `MutableSet<String>` for ease of use.
```kotlin
val stringSetExample = DataStoreValue(
    key = "string-set-example",
    default = emptySet<String>()
)
```

## Usage
### Initialization
Create `object` that extends `DataStoreValues` class and create values needed to be saved.
```kotlin
object SavedValues : DataStoreValues() {
    val stringExample = DataStoreValue(
        key = "string-example",
        default = "String"
    )
    ...
    val booleanExample = DataStoreValue(
        key = "boolean-example",
        default = true
    )
}
```

Initialize `DataStore` with created `objects` inside `MainActivity` class like so:
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ...
        DataStore.start(SavedValues, ..., AnotherValues) // Pass all objects you created
        ...
        setContent {
            // Content here...
        }
    }
}
```

### Accessing values
For access use `DataStore` object with passed value that is needed:
```kotlin
val string = DataStore(SavedValues.booleanExample)
```
Then you can:
- use `read()` or `readAsFlow()`  to read saved values
```kotlin
DataStore(SavedValues.booleanExample).read()
DataStore(SavedValues.booleanExample).readAsFlow().collectAsState(initial = false)
```
- use `update()` for `Synchronously` save new value:
```
DataStore(SavedValues.booleanExample).update(false)
```
or `updateAsync()` for `Asynchronously` save new value from `Coroutine`:
```kotlin
val scope = rememberCoroutineScope()

scope.launch {
    DataStore(SavedValues.booleanExample).updateAsync(false)
}
```
- Exclusively for `boolean values` there are additional functions: `updateInverted()` and `updateInvertedAsync()` which reverses value of boolean.
- use `reset()` or `resetAsync()` to revert saved value to default:
```kotlin
DataStore(SavedValues.booleanExample).reset()
scope.launch {
    DataStore(SavedValues.booleanExample).resetAsync()
}
```

# License
```xml
Copyright 2024 Mykola Petryk

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
