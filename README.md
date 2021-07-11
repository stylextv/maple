<h1 align="center">
  <br>
  <img src="https://raw.githubusercontent.com/StylexTV/LYNX/main/image/cover.png">
  <br>
</h1>

<h4 align="center">👾 Source code of the LYNX bot, made with ❤️ in Java.</h4>

<p align="center">
  <a href="https://GitHub.com/StylexTV/LYNX/stargazers/">
    <img alt="stars" src="https://img.shields.io/github/stars/StylexTV/LYNX.svg?color=ffdd00"/>
  </a>
  <a href="https://www.codacy.com/gh/StylexTV/LYNX/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=StylexTV/LYNX&amp;utm_campaign=Badge_Grade">
    <img alt="Codacy Badge" src="https://app.codacy.com/project/badge/Grade/a1b260d90028428fbf9ce33f4fcf311e"/>
  </a>
  <a>
    <img alt="Code size" src="https://img.shields.io/github/languages/code-size/StylexTV/LYNX.svg"/>
  </a>
  <a>
    <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/StylexTV/LYNX.svg"/>
  </a>
  <a>
    <img alt="Lines of Code" src="https://tokei.rs/b1/github/StylexTV/LYNX?category=code"/>
  </a>
</p>

## Overview
[LYNX](https://playlynx.github.io/) is a free and powerful path-finding bot that lets you navigate the vast landscapes of [Minecraft](https://www.minecraft.net/) without any user intervention.

Additionally, LYNX focuses on an unobtrusive look and feel of being a real person rather than focusing on efficiency and performance.
The bot is mainly controlled by [chat input](https://github.com/StylexTV/LYNX#commands) and the underlying pathing algorithm is called [A*](https://en.wikipedia.org/wiki/A*_search_algorithm), a perfect algorithm for navigating voxel worlds like Minecraft.

If you simply want to use LYNX, you can download the latest version from the [download page](https://playlynx.github.io/). With the appropriate version of [Forge](https://files.minecraftforge.net/net/minecraftforge/forge/) installed, you should be good to go.

## Features
Coming soon...

### 🧭 Long distance travel
Coming soon...

### 📚 Caching
Coming soon...

### 📍 Waypoints
Coming soon...

### 🧱 Breaking/Placing blocks
Coming soon...

### ⛔ Avoiding dangers
Coming soon...

## Commands
All your interactions with the bot take place via the in-game chat functionality.

In order for LYNX to recognize your command, it must start with a `#`.
You can use the following list as a guide or simply use the ingame command `#help`.

Name | Usages | Aliases | Description
--- | --- | --- | ---
help | help [page]<br/>help [command] | - | Gives useful information about commands.
goto | goto \<x> \<y> \<z> [radius]<br/>goto \<x> \<z><br/>goto \<y> | - | Sets a new goal and starts moving to it.
goal | goal \<x> \<y> \<z> [radius]<br/>goal \<x> \<z><br/>goal \<y> | - | Sets a new goal.
go | - | - | Starts moving to the set goal.
stop | - | cancel | Stops all actions.
waypoint | waypoint create \<name> [x y z]<br/>waypoint delete \<name><br/>waypoint list [page]<br/>waypoint goto \<name> | wp | Used to create and travel to waypoints.
home | - | h | Travel to the waypoint named *home*.
lost | - | - | Travel to the nearest waypoint.
axis | - | - | Travel to the nearest axis.
invert | - | - | Inverts the set goal.
modified | modified [page] | - | Shows all modified options.
version | - | ver, v | Shows the installed version of LYNX.

## Options
With the help of options you can better customize the behavior and functionality of LYNX to your personal needs.

Use the command `#help option` in combination with the table of available options below to start customizing the bot.

  * #### allowBreak
    Whether or not the bot should be allowed to break blocks in order to get to the specified goal.
    Disabling could lead to the possibility of the bot not being able to reach the destination.

  * #### allowPlace
    Whether or not the bot should be allowed to place blocks in order to get to the specified goal.
    Disabling could lead to the possibility of the bot not being able to reach the destination.

## Installation & setup
Coming soon...
