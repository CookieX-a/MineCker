# About MineCker 关于MineCker

## 中文 (zh-cn)

### 概述
本模组为 Minecraft Java 版（NeoForge 加载器）提供了一种在游戏内直接执行 Docker 命令的方式。服务端 OP 只需在聊天栏输入以 `!docker` 开头的消息，即可将后续参数传递给系统的 Docker CLI 并执行，执行结果（标准输出和错误输出）会实时返回给玩家。

### 功能特性
- **仅限OP**：仅拥有 OP 权限（等级 4）的玩家可以使用，防止未授权执行。
- **跨平台支持**：自动识别操作系统（Windows / Linux），分别使用 `cmd /c` 或 `bash -c` 执行命令，确保兼容性。
- **安全防护**：内置危险符号黑名单（如 `|`、`&`、`&&`、`;`、`$`、`` ` `` 等），阻止命令注入攻击。
- **完整输出**：同时捕获命令的标准输出和错误输出，若执行成功无输出则提示“命令执行成功，无输出内容”。
- **无额外依赖**：仅需服务端已安装 Docker 环境（Docker Engine 或 Docker Desktop），模组自身不携带 Docker 二进制文件。

### 使用方法
1. 确保服务端的 `docker` 命令可用（在命令行执行 `docker ps` 能正常返回）。
2. 将模组放入 `mods` 文件夹，启动服务端。
3. 以 OP 身份进入游戏，在聊天栏输入：**!docker ps**，即可列出所有运行中的容器。
4. 支持任何 Docker 子命令，例如：
- `!docker images`
- `!docker run hello-world`
- `!docker stop 容器ID`

### 安全提示
- **请勿在不可信的环境中使用**：任何 OP 都可以执行任意 Docker 命令，请确保你的 OP 名单完全可信。
- **危险符号已被拦截**：包含 `|`、`&&` 等符号的命令会被拒绝执行，但这并不能完全避免所有风险（例如 `docker rm -f $(docker ps -aq)` 仍然危险）。建议仅授予可信玩家 OP。

### 环境要求
- 服务端：Minecraft 1.21.8 + NeoForge
- 操作系统：Windows（需安装 Docker Desktop）或 Linux（需安装 Docker Engine 并确保当前用户有执行权限）
- Docker：任何版本均可，只要 `docker` 命令在系统 PATH 中可用

### 文件自动生成
模组在服务端首次加载时，会在服务端根目录下创建 `LocalDockerAPI` 文件夹（可用于存放脚本或配置），不干扰原有文件。

### 开源与许可
本模组采用 [MIT 许可证]。Docker 商标归 Docker, Inc. 所有，模组使用其 Logo 遵循 [Docker 媒体资源使用条款](https://www.docker.com/company/newsroom/media-resources/)

### 其他
由 DeepSeek 协助开发，使用 MCreator 开发。
如果你想要在你的模组中使用这个库，非常简单，只需要发送一个消息"!docker (命令)"就可以了！
下载链接：[Modrinth](https://modrinth.com/plugin/minecker)

---

## English (en-uk)

### Overview
This mod allows Minecraft Java Edition (NeoForge) server operators to execute Docker commands directly from in-game chat. By typing a message starting with `!docker`, the rest of the message is passed to the system’s Docker CLI, and the output (both stdout and stderr) is sent back to the player.

### Features
- **OP Only**: Only players with OP level 4 can use the command.
- **Cross‑platform**: Automatically detects Windows or Linux and uses `cmd /c` or `bash -c` accordingly.
- **Security**: Blocks dangerous symbols (`|`, `&`, `&&`, `;`, `$`, `` ` ``, etc.) to prevent command injection.
- **Full Output**: Captures both standard output and error output. If the command succeeds but produces no output, a friendly message is shown.
- **No Bundled Docker**: The mod does not ship Docker binaries; it relies on a working `docker` command on the host system.

### Usage
1. Ensure `docker ps` works from the server’s command line.
2. Place the mod in the `mods` folder and start the server.
3. Join as an OP and type in chat:**!docker ps**,this will list all running containers.
4. Any Docker subcommand works, for example:
- `!docker images`
- `!docker run hello-world`
- `!docker stop <container-id>`

### Security Notice
- **Use only in trusted environments**: Any OP can run arbitrary Docker commands. Make sure your OP list is fully trusted.
- **Dangerous symbols are blocked** (e.g., `|`, `&&`), but this does not prevent all malicious commands (e.g., `docker rm -f $(docker ps -aq)` is still dangerous). Only grant OP to players you trust.

### Requirements
- Server: Minecraft 1.21.8 + NeoForge
- OS: Windows (with Docker Desktop) or Linux (with Docker Engine, and the server user must have permission to run Docker)
- Docker: Any version that provides a working `docker` command in system PATH.

### Auto‑created Folder
On the first server start, the mod creates a `LocalDockerAPI` folder in the server root directory (can be used for scripts or configurations).

### License & Trademarks
This mod is licensed under the [MIT License]. Docker is a trademark of Docker, Inc. The use of the Docker logo in compliance with [Docker’s media resources guidelines](https://www.docker.com/company/newsroom/media-resources/)

### Other
DeepSeek help me create this mod.Made by MCreator.
If you want to use this lib for you mod, it's very easy, you just need to send a message "!docker (command)"!
Download Link：[Modrinth](https://modrinth.com/plugin/minecker)
