package com.aivazart.navigation.events

sealed interface BodyStatsEvent {
    data class UpdateStatistic(val key: String, val value: String): BodyStatsEvent
    data class ShowDialog(val key: String): BodyStatsEvent
    data class HideDialog(val key: String): BodyStatsEvent
}