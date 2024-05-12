package com.aivazart.navigation.model

data class BodyStatsState(
    val values: Map<String, String> = emptyMap(),
    val dialogVisibility: Map<String, Boolean> = emptyMap()
)
