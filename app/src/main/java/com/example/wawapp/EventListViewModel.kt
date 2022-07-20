package com.example.wawapp

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wawapp.event.store.EventType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.IOException

class EventListViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    var errorHolder: ErrorHolder by mutableStateOf(ErrorHolder(false, ""))

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh(context: Context) {
        viewModelScope.launch {
            _isRefreshing.emit(true)

            withContext(CoroutineScope(IO).coroutineContext) {
                try {
                    EventFetcher.fetch(context, *EventType.values())
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    errorHolder.apply {
                        isError = true
                        message = "An error occurred when fetching events!"
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    errorHolder.apply {
                        isError = true
                        message =
                            "An unexpected error occurred while getting location for an address"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
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
