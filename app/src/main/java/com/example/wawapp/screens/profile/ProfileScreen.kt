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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.Auth
import com.example.wawapp.R
import com.example.wawapp.events.EventStore
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileScreenViewModel = viewModel()) {
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
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
                title = {
                    Text(
                        text = stringResource(id = R.string.profile),
                        fontSize = 24.sp,
                        color = colorResource(id = R.color.accent_color),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    IconButton(
                        onClick = viewModel::showDialog,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null,
                            tint = colorResource(
                                id = R.color.accent_color
                            )
                        )
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
                    CircularProgressIndicator(color = colorResource(id = R.color.accent_color))
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
