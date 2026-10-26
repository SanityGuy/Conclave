# Conclave

<p align="center">
  <strong>Professional staff communication for Paper servers.</strong>
  <br />
  A lightweight, configurable staff chat plugin built for Minecraft server teams.
  <br /><br />
  <img src="https://img.shields.io/badge/Minecraft-Paper-00E5FF?style=flat-square" alt="Paper" />
  <img src="https://img.shields.io/badge/Java-25-00E5FF?style=flat-square" alt="Java 25" />
  <img src="https://img.shields.io/badge/License-MIT-00E5FF?style=flat-square" alt="MIT License" />
  <img src="https://img.shields.io/badge/Formatting-MiniMessage-00E5FF?style=flat-square" alt="MiniMessage" />
</p>

---

## Overview

**Conclave** provides a dedicated communication channel for server staff, keeping internal coordination separate from public chat.

Designed with simplicity and configurability in mind, Conclave supports direct staff messages, automatic staff chat mode, configurable message formatting, permission-based access, and administrative commands.

Whether you're managing a small community or a larger Minecraft server, Conclave gives your team a straightforward way to communicate.

## Features

- **Dedicated Staff Chat** — Send messages directly to authorized staff members.
- **Automatic Staff Chat** — Toggle staff chat mode to redirect your normal messages into the staff channel.
- **Flexible Commands** — Manage staff chat without unnecessary complexity.
- **MiniMessage Formatting** — Customize chat layouts, colors, and styles, including hexadecimal colors.
- **Configurable Prefix** — Personalize Conclave's identity or disable its prefix.
- **Permission-Based Access** — Control who can use and receive staff chat messages.
- **Message Cooldown** — Configure a cooldown for direct staff messages, with permission-based bypass support.
- **Message Length Limit** — Set a maximum length or disable the limit.
- **Console Logging** — Choose whether staff messages are logged to the server console.
- **Live Configuration Reload** — Reload settings without restarting the server.
- **Open Source** — Released under the MIT License.

## Requirements

| Requirement | Version |
|---|---|
| Minecraft server software | Paper |
| Paper API target | 26.3 |
| Java | 25 |

Conclave is developed against the versions listed above. Compatibility with other versions should be verified before deployment.

## Installation

1. Download the Conclave JAR from your chosen release source.
2. Place the JAR inside your server's `plugins/` directory.
3. Start or restart your Paper server.
4. Open `plugins/Conclave/config.yml` to customize the plugin.
5. Adjust permissions to match your staff structure.

To apply configuration changes, use `/conclave reload` if you have permission.

## Commands

| Command | Description |
|---|---|
| `/sc <message>` | Send a direct staff message |
| `/sc` | Toggle automatic staff chat |
| `/staffchat <message>` | Send a direct staff message |
| `/sctoggle` | Toggle automatic staff chat |
| `/staffchattoggle` | Toggle automatic staff chat |
| `/conclave help` | Display available Conclave commands |
| `/conclave version` | Display plugin version information |
| `/conclave reload` | Reload the configuration |

### Aliases

| Command | Aliases |
|---|---|
| `/staffchat` | `/sc`, `/conclavesc` |
| `/staffchattoggle` | `/sctoggle`, `/conclavesctoggle`, `/conclavetoggle` |
| `/conclave` | `/cv`, if configured |

## Permissions

| Permission | Purpose | Suggested default |
|---|---|---|
| `conclave.staffchat` | Send and receive staff chat messages | Operators |
| `conclave.staffchat.bypass-cooldown` | Bypass the direct-message cooldown | Operators |
| `conclave.help` | Access the help command | Everyone |
| `conclave.version` | View plugin version information | Everyone |
| `conclave.reload` | Reload the configuration | Operators |

Permission defaults are controlled by `plugin.yml` and may be overridden by your permissions plugin.

**Recommended setup:** Grant `conclave.staffchat` only to trusted staff members. The cooldown bypass permission should be reserved for users who need it.

## Configuration

Conclave generates its configuration at:

`plugins/Conclave/config.yml`

### Example

```yaml
general:
  enabled: true
  prefix-enabled: true
  prefix: "<#00E5FF><bold>Conclave</bold></#00E5FF> <dark_gray>·</dark_gray> "

chat:
  format: "<dark_gray>[<#00E5FF><bold>SC</bold></#00E5FF><dark_gray>]</dark_gray> <white><player> <#00E5FF>»</#00E5FF> <reset><message>"
  console-log: true
  cooldown-seconds: 3
  max-length: 128

messages:
  no-permission: "<red>You don't have permission to use this."
  usage: "<red>Usage: <white>/sc <message>"
  disabled: "<red>Staff chat is off. Use <white>/sctoggle <red>to turn it on."
  toggle-on: "<#00E5FF>Staff chat <bold>enabled</bold>."
  toggle-off: "<#00E5FF>Staff chat <bold>disabled</bold>."
  cooldown: "<red>Slow down. Try again in <white>{seconds}s<red>."
  too-long: "<red>Message is too long (max <white>{max}<red> characters)."

permissions:
  base: "conclave.staffchat"
  bypass-cooldown: "conclave.staffchat.bypass-cooldown"
```

### General settings

| Setting | Description |
|---|---|
| `general.enabled` | Enables or disables Conclave's staff chat functionality. |
| `general.prefix-enabled` | Enables or disables the prefix on plugin feedback messages. |
| `general.prefix` | Sets the prefix displayed on plugin feedback messages. |

### Chat settings

| Setting | Description |
|---|---|
| `chat.format` | Defines the staff chat message layout. |
| `chat.console-log` | Enables or disables staff message logging in the console. |
| `chat.cooldown-seconds` | Sets the cooldown between direct staff messages. |
| `chat.max-length` | Sets the maximum allowed message length. |

Set `cooldown-seconds` or `max-length` to `0` to disable the respective restriction.

### Message formatting

Conclave uses [MiniMessage](https://docs.advntr.dev/minimessage/format.html) for formatting.

The chat format supports these placeholders:

| Placeholder | Description |
|---|---|
| `<player>` | The sender's display name component |
| `<message>` | The message content |

Example:

```yaml
chat:
  format: "<dark_gray>[<#00E5FF>STAFF</#00E5FF>]</dark_gray> <white><player> <dark_gray>»</dark_gray> <message>"
```

Hexadecimal colors are supported:

```text
<#00E5FF>Cyan text</#00E5FF>
```

**Tip:** MiniMessage supports many additional formatting options, including gradients, decorations, hover events, and click events. See the official documentation for details.

## Building from source

Conclave is intended to be built using its configured Java build system.

For a Gradle project, run:

```bash
./gradlew build
```

On Windows:

```powershell
.\gradlew.bat build
```

The generated JAR will normally be located in `build/libs/`.

## Contributing

Contributions, bug reports, and suggestions are welcome.

1. Fork the repository.
2. Create a branch for your changes.
3. Make your changes and test them on a compatible Paper server.
4. Submit a pull request describing the changes.

Please keep contributions focused, maintain readable code, and document new configuration options.

## License

Conclave is licensed under the **MIT License**. You are free to use, modify, distribute, and include it in commercial projects, subject to the license terms.

See [`LICENSE`](LICENSE) for the complete license text.

## Author

**SouyanDev**

- Website: https://www.souyande.xyz
- GitHub: https://github.com/SanityGuy

---

<p align="center">
  <strong>Conclave</strong>
  <br />
  <sub>Clear communication. Better coordination.</sub>
</p>