plugins {
    id("marvel.android.library")
    id("marvel.android.library.compose")
}

android {
    namespace = "com.sawrose.marvelapp.core.designsystem"

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt.compose)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
}