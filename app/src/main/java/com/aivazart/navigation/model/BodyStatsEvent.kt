package com.aivazart.navigation.model

sealed interface BodyStatsEvent {
    data class UpdateStatistic(val key: String, val value: String): BodyStatsEvent
    data class ShowDialog(val key: String): BodyStatsEvent
    data class HideDialog(val key: String): BodyStatsEvent
}