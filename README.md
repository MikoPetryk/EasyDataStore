<h1 align="center">EasyDataStore</h1><br>

<p align="center">
	<a href="https://jitpack.io/#MikoPetryk/EasyDataStore/1.0.1.2"><img alt="jitpack" src="https://jitpack.io/v/MikoPetryk/EasyDataStore.svg"/></a>
	<a href="https://github.com/MikoPetryk"><img alt="github" src="https://img.shields.io/badge/Github-Miko%20Petryk-blue"/></a>
</p>


<p align="center">
EasyDataStore is an Android library that simplifies the usage of DataStore. 
With EasyDataStore, you can easily define, access, and modify your settings values using a single object.
</p>

## Download
[![](https://jitpack.io/v/MikoPetryk/EasyDataStore.svg)](https://jitpack.io/#MikoPetryk/EasyDataStore/1.0.1.2)<br>

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
 implementation 'com.github.MikoPetryk:EasyDataStore:1.0.1.2'
}
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
###Accessing values
For access use `DataStore` object with passed value that is needed:
```kotlin
val string = DataStore(SavedValues.stringExample)
```
Then you can:
- use `read()` or `readAsFlow()`  to read saved values
```kotlin
DataStore(SavedValues.stringExample).read()
DataStore(SavedValues.stringExample).readAsFlow().collectAsState(initial = false)
```
- use `update()` for `Synchronously` save new value:
```
DataStore(SavedValues.stringExample).update(false)
```
or `updateAsync()` for `Asynchronously` save new value from `Coroutine`:
```
val scope = rememberCoroutineScope()

scope.launch {
 DataStore(SavedValues.stringExample).updateAsync(false)
}
```
- Exclusively for boolean values there is additional functions - `updateInverted()` and `updateInvertedAsync()` which reverses value of boolean.
- use `reset()` or `resetAsync()` to revert saved value to default:
```kotlin
DataStore(SavedValues.stringExample).reset()
scope.launch {
 DataStore(SavedValues.stringExample).updateAsync(false)
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
