本分支完整支持 Minecraft 1.21.10，NeoForge 依赖范围为 `[21.10,)`，使用 Java 21。

保留 TPS、MSPT、生物生成上限与玩家列表标签，并继续通过 RollingGate 配置规则。

构建命令为 `./gradlew.bat compileJava build --console=plain`，输出 `build/libs/bettertab-1.0.0-mc1.21.10.jar`。构建时优先使用相邻目录中的同版本 RollingGate 源码，可用 `-Prolling_gate_source=路径` 指定源码位置。
