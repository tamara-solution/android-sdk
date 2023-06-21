## Tamara Android SDK
### Export aar file
To export aar file from the SDK run this command from root folder of project
```
./gradlew sdk:assembleRelease
```

Aar file can be found in this folder: sdk\build\outputs\aar
Rename file to tamara-sdk.aar

### Use aar file as library
Copy aar file to your app libs folder

In app level build.gradle, add tamara-sdk.aar as libary:
```
implementation (name:'tamara-sdk', ext:'aar')
```

In project level build.gradle:
```
allprojects {
    repositories {
        flatDir {
            dirs 'libs'
        }
    }
}
```
Sync project with gradle and start the intergration