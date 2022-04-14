package tech.tennoji.igncodefoo.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import tech.tennoji.igncodefoo.network.Api
import tech.tennoji.igncodefoo.network.VideoData

class VideoViewModel :ViewModel() {
    private var viewModelJob = SupervisorJob()

    private val _videoList = MutableLiveData<List<VideoData>>()
    val videoList:LiveData<List<VideoData>>
    get() = _videoList

    private val _openLink = MutableLiveData<String?>()
    val openLink: LiveData<String?>
        get() = _openLink

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    fun getVideoData(startIndex: Int, count: Int) {
        coroutineScope.launch {
            val articleDeferred = Api.retrofitService.getVideoListAsync(startIndex, count)
            try {
                val videoResponse =
                    articleDeferred.await()
                val contentIdList = ArrayList<String>()
                videoResponse.data.forEach {
                    contentIdList.add(it.contentId)
                }
                val contentIds = contentIdList.joinToString(",")
                val commentCountResponse =
                    Api.retrofitService.getCommentCountAsync(contentIds).await()
                for (i in 0 until commentCountResponse.count) {
                    for (j in 0 until commentCountResponse.count) {
                        if (videoResponse.data[i].contentId == commentCountResponse.content[j].id) {
                            videoResponse.data[i].commentCount =
                                commentCountResponse.content[j].count
                        }
                    }
                }
                _videoList.postValue(videoResponse.data)
            } catch (e: Exception) {
                onError()
            }
        }
    }

    fun onCardClick(slug: String) {
        _openLink.value = slug
    }

    fun onOpenLinkComplete() {
        _openLink.value = null
    }

    private fun onError() {
        _error.postValue(true)
    }

    fun onErrorToastComplete() {
        _error.value = false
    }


}