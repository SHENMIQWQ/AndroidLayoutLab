# Android 界面布局实验

本项目用一个 Android 应用完成实验中的五个界面：

- `LinearLayoutActivity`：4 x 4 线性布局网格
- `TableLayoutActivity`：Hello TableLayout 菜单
- `ConstraintOneActivity`：ConstraintLayout 计算器
- `ConstraintTwoActivity`：ConstraintLayout 太空旅行页面
- `ComposeActivity`：可添加、勾选、删除任务的 Compose 页面

## 运行

克隆仓库后，用 Android Studio 打开项目目录，等待 Gradle Sync 后运行 `app`。命令行构建：

```powershell
.\gradlew.bat :app:assembleDebug
```

调试 APK 位于 `app\build\outputs\apk\debug\app-debug.apk`。

项目使用 Gradle 9.5.0、JDK 17 和 Android SDK 37。依赖仓库配置了阿里云镜像，Gradle Wrapper 从官方地址下载。
