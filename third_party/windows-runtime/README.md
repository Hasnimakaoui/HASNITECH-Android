# Windows runtime native sources

HASNITECH currently uses Jetpack Compose and does not yet bundle a Windows compatibility runtime.

The upstream Winlator source is maintained separately:
- https://github.com/brunodev85/winlator
- https://github.com/winebox64/winlator

Runtime components that need to be built and packaged for Android ARM64 include Wine, Box64 (and Box86 where required), PRoot/root filesystem, Mesa/Turnip or another supported graphics backend, DXVK, and VKD3D. These are not interchangeable Maven dependencies; they include native binaries, Android-specific build steps, and runtime assets.

## Integration status

This file documents the upstream source references only. It does **not** claim that these native components are bundled into the HASNITECH APK. A real in-process integration requires porting/merging the upstream Android app and its native build system, resolving application/package/resource conflicts, and validating the resulting APK on-device. Until that work is complete, HASNITECH must keep treating Winlator as an external runtime.


## QEMU Windows disk provisioning

The Android QEMU build does not itself install Windows. This repository intentionally does not commit a Microsoft ISO or a preinstalled Windows disk image. To create a bootable disk, use a properly licensed Windows ISO obtained from Microsoft and install it into a QCOW2 disk with QEMU on a host machine. Copy the resulting disk image to the device and configure the HASNITECH QEMU machine to use it as its primary drive.

For ARM64 Android, the app's QEMU engine is a **host ARM64, x86_64 guest** emulator using software translation. Performance for a full x86_64 Windows desktop on mid-range mobile hardware may be very poor. This is a technical constraint, not a dashboard setting.

The release is not considered "Windows preinstalled" until a Windows disk image is packaged or provisioned and an actual guest boot is verified. Do not label a QEMU-only APK as a Windows installation.
