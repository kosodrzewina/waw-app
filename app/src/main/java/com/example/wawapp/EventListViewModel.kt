package com.example.wawapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

class EventListViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    var errorHolder: ErrorHolder by mutableStateOf(ErrorHolder(false, ""))

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            withContext(CoroutineScope(IO).coroutineContext) {
                try {
                    EventFetcher.fetch(*EventType.values())
                } catch (fileNotFound: FileNotFoundException) {
                    errorHolder.apply {
                        isError = true
                        message = "An error occurred when fetching events!"
                    }
                } catch (e: Exception) {
                    val error = e.toString()
                    println(error)

                    errorHolder.apply {
                        isError = true
                        message = "Unknown error occurred!"
                    }
                }
                _isRefreshing.emit(false)
            }
        }
    }
}
