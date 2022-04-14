package tech.tennoji.igncodefoo.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VideoViewModelFactory :ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)){
            return VideoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}