# Android Live Wallpaper Minimal Showcase

Minimal demo project of live wallpapers set via Intent and also installed and set manually 

Based on the following resources:
* [YouTube video to develop a simple live wallpaper service - no activity](https://www.youtube.com/watch?v=Tym8lRg72GA)
* [GIF Wallpaper github project by milaptank](https://github.com/milaptank/GIFLiveWallpaper/) 

# Project Background
The live wallpapers themselves are intentionally simple. I made this project more to showcase how the `AndroidManifest.xml` should be structured, as well as to show various ways the live wallpaper can be installed and run. 

## Running the Project
- Clone this repository
- Open the project in Android Studio
- Connect an Android device or Emulator

### With-Activity Version
This version installs an app which the user can open from their home screen.

From the app activity, the user can press the appropriate button to initiate the intent to set a live wallpaper using the `WallpaperManager` class.

The Android Studio Run configuration launch option should be set to `Default Activity`. Or you can open the app manually after it is installed onto your emulator or device.

#### Touch Wallpaper
A simple touch wallpaper that renders a circle where the device detects a touch input. Does not differentiate between gestures.

#### Animated GIF Wallpaper
This example uses an `InputStream` to decode a GIF or possibly another movie file to the Android `Movie` class. This can then be rendered to the canvas with scaling and position offsets. 

### No-Activity Version
This version does not install an app to the home screen. It simply installs live wallpapers.

Set the Android Studio run configuration to `Nothing` in the launch options.

After installing, the user must manually set the live wallpaper they want on the android device/emulator. How this is done varies for each device.

# Art Assets Licensing
Creative Commons 3.0 Attribution to [Nelson Yiap](https://www.nelsonyiap.com)
 
[Read License Details](https://creativecommons.org/licenses/by/3.0/)