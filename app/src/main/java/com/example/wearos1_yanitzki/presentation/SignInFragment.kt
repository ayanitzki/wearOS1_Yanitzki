package com.example.wearos1_yanitzki.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*

class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPrefs = requireContext().getSharedPreferences("WorldCinemaPrefs", Context.MODE_PRIVATE)
        val savedEmail = sharedPrefs.getString("USER_EMAIL", "") ?: ""
        val savedPassword = sharedPrefs.getString("USER_PASSWORD", "") ?: ""

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    SignInScreen(
                        initialEmail = savedEmail,
                        initialPassword = savedPassword,
                        onSignInClick = { email, password ->
                            sharedPrefs.edit()
                                .putString("USER_EMAIL", email.trim())
                                .putString("USER_PASSWORD", password)
                                .apply()

                            (activity as? MainActivity)?.navigateTo(MainMenuFragment())
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SignInScreen(
    initialEmail: String,
    initialPassword: String,
    onSignInClick: (String, String) -> Unit
) {
    val listState = rememberScalingLazyListState()
    var email by remember { mutableStateOf(initialEmail) }
    var password by remember { mutableStateOf(initialPassword) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
    ) {
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp)
        ) {
            item {
                Text(
                    text = "WorldCinema",
                    color = Color(0xFF3B79B8),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            item {
                WearInputField(
                    label = "E-mail",
                    placeholder = "you@example.com",
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = null
                    }
                )
            }
            item {
                WearInputField(
                    label = "Пароль",
                    placeholder = "Ваш пароль",
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = null
                    },
                    isPassword = true
                )
            }
            if (errorMessage != null) {
                item {
                    Text(
                        text = errorMessage!!,
                        color = Color(0xFFE53935),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            item {
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Заполните все поля"
                        } else {
                            errorMessage = null
                            onSignInClick(email, password)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3B79B8)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .height(38.dp)
                ) {
                    Text(text = "Войти", color = Color.White, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun WearInputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = label, color = Color(0xFF3B79B8), fontSize = 10.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Box {
            if (value.isEmpty()) {
                Text(text = placeholder, color = Color.Gray, fontSize = 12.sp)
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
                cursorBrush = SolidColor(Color(0xFF3B79B8)),
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(top = 2.dp)
                .background(Color(0xFF3B79B8))
        )
    }
}