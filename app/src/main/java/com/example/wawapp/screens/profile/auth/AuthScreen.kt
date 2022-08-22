package com.example.wawapp.screens.profile.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wawapp.Auth
import com.example.wawapp.R
import com.example.wawapp.screens.profile.ProfileScreen
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(navController: NavController, viewModel: AuthScreenViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val logInGoodString = stringResource(id = R.string.log_in_good)
    val logInBadString = stringResource(id = R.string.log_in_bad)
    val registrationGoodString = stringResource(id = R.string.registration_good)
    val registrationBadString = stringResource(id = R.string.registration_bad)

    Scaffold(scaffoldState = scaffoldState) {
        if (Auth.token != null) {
            Auth.email?.let { email ->
                ProfileScreen(email = email, navController = navController)
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Text(
                    text = stringResource(
                        id = if (viewModel.isRegistration)
                            R.string.registration
                        else
                            R.string.login
                    ),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .weight(1f)
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 20.dp)
                        .fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = viewModel.loginValue,
                            onValueChange = viewModel::onLoginValueChange,
                            enabled = !viewModel.isContactingServer,
                            label = { Text(text = stringResource(id = R.string.email)) },
                            modifier = Modifier
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                                .fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = viewModel.passwordValue,
                            onValueChange = viewModel::onPasswordValueChange,
                            enabled = !viewModel.isContactingServer,
                            label = { Text(text = stringResource(id = R.string.password)) },
                            visualTransformation = if (viewModel.isPasswordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = viewModel::switchPasswordVisibility) {
                                    Icon(
                                        imageVector = if (viewModel.isPasswordVisible)
                                            Icons.Filled.Visibility
                                        else
                                            Icons.Filled.VisibilityOff,
                                        contentDescription = null
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                                .fillMaxWidth()
                        )
                        if (viewModel.isRegistration) {
                            OutlinedTextField(
                                value = viewModel.retypedPasswordValue,
                                onValueChange = viewModel::onRetypedPasswordValueChange,
                                enabled = !viewModel.isContactingServer,
                                label = { Text(text = stringResource(id = R.string.retyped_password)) },
                                visualTransformation = if (viewModel.isRetypedPasswordVisible)
                                    VisualTransformation.None
                                else
                                    PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = viewModel::switchRetypedPasswordVisibility) {
                                        Icon(
                                            imageVector = if (viewModel.isRetypedPasswordVisible)
                                                Icons.Filled.Visibility
                                            else
                                                Icons.Filled.VisibilityOff,
                                            contentDescription = null
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = viewModel.isRegistration,
                                onCheckedChange = viewModel::setIsRegistration,
                                enabled = !viewModel.isContactingServer
                            )
                            Text(
                                text = stringResource(id = R.string.first_time),
                                color = if (viewModel.isContactingServer)
                                    Color.Gray
                                else
                                    Color.Black
                            )
                        }
                        Button(
                            shape = RoundedCornerShape(24.dp),
                            onClick = {
                                coroutineScope.launch {
                                    val response = viewModel.authenticate()

                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            if (response)
                                                if (viewModel.isRegistration)
                                                    registrationGoodString
                                                else
                                                    logInGoodString
                                            else
                                                if (viewModel.isRegistration)
                                                    registrationBadString
                                                else
                                                    logInBadString
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            if (viewModel.isContactingServer) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Text(
                                    text = stringResource(
                                        id = if (viewModel.isRegistration)
                                            R.string.register
                                        else
                                            R.string.log_in
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
