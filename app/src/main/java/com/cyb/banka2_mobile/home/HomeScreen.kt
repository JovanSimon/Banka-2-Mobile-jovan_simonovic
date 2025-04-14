package com.cyb.banka2_mobile.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.banka2_mobile.home.card_preview.AccountCardsSection
import com.cyb.banka2_mobile.home.card_preview.TransactionList
import com.cyb.banka2_mobile.home.card_preview.TransferAndQrActions
import com.cyb.banka2_mobile.home.top_screen_card.TopScreenCard
import com.cyb.banka2_mobile.ui.theme.EnableEdgeToEdge

fun NavGraphBuilder.home(
    route: String
) = composable(
    route = route
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val state = homeViewModel.state.collectAsState()
    EnableEdgeToEdge()
    HomeScreen(
        state = state.value,
        eventPublisher = {
            homeViewModel.setEvent(it)
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeContract.HomeState,
    eventPublisher: (uiEvent: HomeContract.HomeEvent) -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF0F1120),
        topBar = {}
    ) { padding ->
        if (state.loading) {
            ShimmerContent(padding)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                TopScreenCard(
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (state.cards.isNotEmpty()) {
                    AccountCardsSection(state.cards)

                    Spacer(modifier = Modifier.height(24.dp))

                    TransferAndQrActions()
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Transactions",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (state.transactions.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No transactions available",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    TransactionList(state.transactions)
                }
            }
        }
    }
}
