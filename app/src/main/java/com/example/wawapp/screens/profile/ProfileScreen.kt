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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.Auth
import com.example.wawapp.R
import com.example.wawapp.events.EventStore
import com.example.wawapp.screens.events.IllustrationView
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
                }
            } else if (EventStore.favouriteEvents.isEmpty()) {
                IllustrationView(
                    drawableId = R.drawable.no_favourites,
                    text = stringResource(id = R.string.no_favourites)
                )
            } else {
                FavouriteEventList(
                    events = EventStore.favouriteEvents,
                    Header = {
                        Column {
                            Text(
                                text = email,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp)
                            )
                            Text(
                                text = "${stringResource(id = R.string.liked_events)}:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Thin,
                                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                            )
                        }
                    },
                    navController = navController
                )
            }
        }
    }
}
