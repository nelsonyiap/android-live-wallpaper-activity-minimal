# Live Wallpaper Minimal Example Project

Minimal demo project of a live wallpaper set via Intent and also installed and set manually 

Based on the following video example and various google searches:
[YouTube video to develop a simple live wallpaper service - no activity](https://www.youtube.com/watch?v=Tym8lRg72GA)

# Project Background
I made this project because I was unable to find any up to date repositories showing how to simply and minimally develop a live wallpaper.

The live wallpaper itself in this project is intentionally simple. I made this project more to showcase how the `AndroidManifest.xml` should be structured, as well as show various ways the live wallpaper can be installed or run. 

## Running the Live Wallpaper
- Clone this project
- Open the project in Android Studio
- Connect an Android device or Emulator

### With-Activity Version
This version installs an app which the user can open from their home screen.

From the app activity, the user can press the button in the middle to initiate the intent to set the live wallpaper using the `WallpaperManager` class.

The Android Studio Run configuration launch option should be set to `Default Activity`. Or you can open the app manually after it is installed onto your emulator or device. 

### No-Activity Version
This version does not install an app to the home screen. It simply installs an apk which is a live wallpaper.

Set the Android Studio run configuration to `Nothing` in the launch options.

After installing, you must manually set the live wallpaper on your android device/emulator. This varies for each device.
