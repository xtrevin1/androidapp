// MainActivity.kt
package com.example.yourapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.myapp.ui.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val test = Test()
        setContent {
            AppNavigation()
        }
    }
}

class Test {
    val name = "Philipp"
}

val filteredChat = combine(
    filterFlow,
    chatRepository.chats
) { filters, chats ->
    filters.fold(chats) { filteredChats, filter ->
        if (filter.isEnabled) {
            when (filter.type) {
                ChatFilterType.UNREAD -> filteredChats.filter { it.hasUnreadMessage == true }
                ChatFilterType.LOCATIONS -> {
                    val selectedLocations = filter.getEnabledOptions()
                    if (selectedLocations.isNotEmpty()) {
                        filteredChats.filter { selectedLocations.contains(it.messageFrom) }
                    } else filteredChats
                }
                else -> filteredChats
            }
        } else {
            filteredChats
        }
    }
}.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = chatRepository.chats.value
)
