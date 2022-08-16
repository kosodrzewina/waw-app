package com.example.wawapp.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wawapp.R

@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = viewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
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
                    label = { Text(text = stringResource(id = R.string.email)) },
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = viewModel.passwordValue,
                    onValueChange = viewModel::onPasswordValueChange,
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
                        onCheckedChange = viewModel::setIsRegistration
                    )
                    Text(text = stringResource(id = R.string.first_time))
                }
                Button(
                    shape = RoundedCornerShape(24.dp),
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
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
