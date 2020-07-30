# RisingFlipNumber

0️⃣ 1️⃣ 2️⃣ 3️⃣ 4️⃣ 5️⃣ 6️⃣ 7️⃣ 8️⃣ 9️⃣

Flip number view for android 

![Recording_2020-06-01-17-57-20](https://user-images.githubusercontent.com/62924824/83393602-0193b980-a432-11ea-83eb-da788f81ce85.gif)

## Usage

### Gradle:
project gradle:
```xml
allprojects {
    repositories {
        maven { url "https://www.jitpack.io" }
    }
}
```
app gradle:
```xml
dependencies {
    implementation 'com.github.risingpark:risingflipnumber:1.0.0'
}
```


### In layout
```xml
    <com.risingpark.risingflipnumber.RisingFlipMeter
        android:id="@+id/flip_meter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:visible_comma="false"                                             
        app:digit="7"/>
```
### Attributes
|Attributes|format|describe
|---|---|---|
|digit|integer| number count
|visible_comma|boolean| x,xxx 

### In code

Setting Number data.
```kotlin
flip_meter.setValue(12345, true)
```
Initialize all number 0
```kotlin
flip_meter.clear()
```

# Download and Install Demo App
https://appdistribution.firebase.dev/i/23d388aea8240b6f

# License
```xml
Copyright 2020 RisingPark

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
