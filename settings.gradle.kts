dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven (url = "https://jitpack.io")
        maven(url ="https://maven.aliyun.com/repository/jcenter")
    }
}
rootProject.name = "MF"
include (":app")
