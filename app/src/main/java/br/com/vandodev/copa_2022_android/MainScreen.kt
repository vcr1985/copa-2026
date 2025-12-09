package br.com.vandodev.copa_2022_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.vandodev.copa_2022_android.ui.TeamColors
import br.com.vandodev.copa_2022_android.ui.isDark
import br.com.vandodev.domain.model.Match
import br.com.vandodev.domain.model.Team
import coil.compose.AsyncImage
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Copa 2026",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            state.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.errorMessage!!, modifier = Modifier.padding(16.dp), textAlign = TextAlign.Center)
                }
            }
            state.matches.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No matches found.", modifier = Modifier.padding(16.dp), textAlign = TextAlign.Center)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(state.matches.size) { index ->
                        val match = state.matches[index]
                        val teamColor = TeamColors.getColorForTeam(match.teamA.name)
                        MatchItem(
                            match = match, 
                            onNotificationClick = viewModel::toggleNotification,
                            backgroundColor = teamColor,
                            contentColor = if (teamColor.isDark()) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MatchItem(match: Match, onNotificationClick: (Match) -> Unit, backgroundColor: Color, contentColor: Color) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamInfo(team = match.teamA, contentColor = contentColor)
                Text(
                    text = "X",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = contentColor
                )
                TeamInfo(team = match.teamB, contentColor = contentColor)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                Text(text = "Data: ${match.date.format(formatter)}", color = contentColor)
                Image(
                    imageVector = if (match.notificationEnabled) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                    contentDescription = null,
                    modifier = Modifier.clickable { onNotificationClick(match) }
                )
            }
        }
    }
}

@Composable
fun TeamInfo(team: Team, contentColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        AsyncImage(
            model = team.flag,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Text(text = team.name, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center, color = contentColor)
    }
}
