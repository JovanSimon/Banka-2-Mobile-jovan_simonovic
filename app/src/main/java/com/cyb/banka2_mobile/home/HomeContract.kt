package com.cyb.banka2_mobile.home

import androidx.compose.runtime.Immutable
import com.cyb.banka2_mobile.home.models.CardUiModel
import com.cyb.banka2_mobile.home.models.HomeUiModel
import com.cyb.banka2_mobile.home.models.TransactionUiModel

interface HomeContract {
    @Immutable
    data class HomeState(
        val loading: Boolean = false,
        val homeUiModel: HomeUiModel? = null,
        val cards: List<CardUiModel> = emptyList(),
        val transactions: List<TransactionUiModel> = emptyList()
    )

    sealed class HomeEvent{
        data object TestEvent: HomeEvent()
    }
}