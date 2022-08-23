package com.example.wawapp.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.Auth
import com.example.wawapp.R
import com.example.wawapp.events.EventStore
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    email: String,
    navController: NavController,
    viewModel: ProfileScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchFavouriteEvents()
    }

    if (viewModel.isLogOutDialog) {
        LogOutAlertDialog(
            onDismissRequest = viewModel::closeDialog,
            onConfirmClick = {
                viewModel.closeDialog()
                coroutineScope.launch {
                    Auth.logOut(context)
                }
            },
            onDismissClick = viewModel::closeDialog
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.profile)) },
                actions = {
                    IconButton(onClick = viewModel::showDialog) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (viewModel.isFetchingFavourites) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = stringResource(id = R.string.loading_your_profile),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                FavouriteEventList(
                    events = EventStore.favouriteEvents,
                    navController = navController
                )
            }
        }
    }
}
