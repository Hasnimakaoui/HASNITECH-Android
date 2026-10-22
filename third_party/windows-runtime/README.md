# Windows runtime native sources

HASNITECH currently uses Jetpack Compose and does not yet bundle a Windows compatibility runtime.

The upstream Winlator source is maintained separately:
- https://github.com/brunodev85/winlator
- https://github.com/winebox64/winlator

Runtime components that need to be built and packaged for Android ARM64 include Wine, Box64 (and Box86 where required), PRoot/root filesystem, Mesa/Turnip or another supported graphics backend, DXVK, and VKD3D. These are not interchangeable Maven dependencies; they include native binaries, Android-specific build steps, and runtime assets.

## Integration status

This file documents the upstream source references only. It does **not** claim that these native components are bundled into the HASNITECH APK. A real in-process integration requires porting/merging the upstream Android app and its native build system, resolving application/package/resource conflicts, and validating the resulting APK on-device. Until that work is complete, HASNITECH must keep treating Winlator as an external runtime.
