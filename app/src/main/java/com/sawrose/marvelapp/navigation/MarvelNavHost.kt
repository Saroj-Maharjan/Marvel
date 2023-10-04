package com.sawrose.marvelapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.sawrose.marvelapp.composable.MarvelAppState
import com.sawrose.marvelapp.feature.characters.navigation.characterRoute
import com.sawrose.marvelapp.feature.characters.navigation.characterScreen
import com.sawrose.marvelapp.feature.favourite.navigation.favouriteScreen

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun MarvelNavHost(
    appState: MarvelAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = characterRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        characterScreen(onCharacterClick = {})
        favouriteScreen(onFavouriteClick = {})
    }
}