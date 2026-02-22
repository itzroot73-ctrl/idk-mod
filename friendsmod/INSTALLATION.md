# Friends Mod - Installation Guide

## Overview
Friends Mod is a comprehensive Minecraft mod that adds a custom Friends System with voice chat and world invites for offline/custom launcher players.

## Requirements
- Minecraft 1.20.1
- Fabric Loader 0.15.11 or higher
- Fabric API

## Installation

### 1. Download the Mod
1. Download the latest release from the [GitHub Releases page](https://github.com/friendsmod/friendsmod/releases)
2. Or build from source using the instructions below

### 2. Install Fabric
1. Download the Fabric Installer from [fabricmc.net](https://fabricmc.net/use/)
2. Run the installer and select "Install Client"
3. Choose your Minecraft installation directory

### 3. Add the Mod
1. Open the Minecraft launcher and select the Fabric profile
2. Click "Edit Profile" → "Open Game Dir"
3. Navigate to the `mods` folder
4. Place the `friendsmod-1.0.0.jar` file in this folder

### 4. Launch Minecraft
1. Select the Fabric profile in the Minecraft launcher
2. Click "Play"
3. The mod will automatically load

## Building from Source

### Prerequisites
- Java 17 or higher
- Git
- Gradle

### Build Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/friendsmod/friendsmod.git
   cd friendsmod
   ```

2. **Build the mod:**
   ```bash
   ./gradlew build
   ```
   On Windows:
   ```bash
   gradlew build
   ```

3. **Find the built mod:**
   The mod JAR will be located at:
   ```
   build/libs/friendsmod-1.0.0.jar
   ```

4. **Install the mod:**
   Copy the JAR file to your Minecraft `mods` folder as described above.

## Usage

### Main Menu
- A new "Friends" button appears on the main menu
- Click it to open the login screen

### Login Screen
- Enter a custom username
- Click "Login" to access the Friends System
- You can skip login to use default settings

### Friends GUI
- View your skin preview
- See your username and auto-generated UID
- Copy your UID to clipboard
- Change your skin
- Add friends using their UID

### World Invites
- When entering a singleplayer world, you'll be prompted to invite friends
- Select friends from your list
- Friends can accept and join your world

### Voice Chat
- Voice chat is enabled by default
- Works only between friends in the same world
- Proximity-based audio with distance attenuation
- Toggle with the configured key binding

## Configuration

The mod creates configuration files in your Minecraft directory:
- `friendsmod_data.json` - Player data and settings
- `friendsmod_friends.json` - Friend list
- `friendsmod_config.json` - Mod configuration

## Key Bindings
- **F** - Open Friends GUI (configurable)
- **Voice Chat Toggle** - Configurable in settings

## Troubleshooting

### Mod Not Loading
1. Ensure you're using the correct Minecraft version (1.20.1)
2. Check that Fabric Loader is installed correctly
3. Verify the mod JAR is in the `mods` folder
4. Check the log file for error messages

### Friends Not Connecting
1. Ensure both players have the mod installed
2. Verify the UID is copied correctly
3. Check that both players are online
4. Ensure the host is in a singleplayer world

### Voice Chat Not Working
1. Check that audio permissions are granted
2. Ensure microphone is properly connected
3. Verify voice chat is enabled in settings
4. Check that both players are in the same world

## Permissions

The mod requires the following permissions:
- Network access for friend connections
- Audio access for voice chat

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This mod is licensed under the MIT License.

## Support

For issues and support:
- Create an issue on the [GitHub repository](https://github.com/friendsmod/friendsmod/issues)
- Check the FAQ section in the mod's help menu

## Changelog

### Version 1.0.0
- Initial release with all core features
- Friends system with UID management
- Voice chat with proximity detection
- World invite system
- Custom UI with glass effects
- JSON-based configuration storage