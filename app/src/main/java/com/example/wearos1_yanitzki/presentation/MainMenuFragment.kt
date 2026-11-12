package com.example.wearos1_yanitzki.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.wear.compose.material.*

class MainMenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    MainMenuScreen(
                        onChatsClick = { (activity as? MainActivity)?.navigateTo(ChatListFragment()) },
                        onMoviesClick = { (activity as? MainActivity)?.navigateTo(MoviesFragment()) },
                        onFavoritesClick = { (activity as? MainActivity)?.navigateTo(MoviesFragment()) }
                    )
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen(
    onChatsClick: () -> Unit,
    onMoviesClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuCircleButton(
                icon = Icons.Outlined.ChatBubbleOutline,
                label = "Обсуждения",
                onClick = onChatsClick
            )
            MenuCircleButton(
                icon = Icons.Outlined.Movie,
                label = "Подборки",
                onClick = onMoviesClick
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        MenuCircleButton(
            icon = Icons.Outlined.FavoriteBorder,
            label = "Избранное",
            onClick = onFavoritesClick
        )
    }
}

@Composable
fun MenuCircleButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2864A0)),
            modifier = Modifier.size(46.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, color = Color.White, fontSize = 10.sp)
    }
}