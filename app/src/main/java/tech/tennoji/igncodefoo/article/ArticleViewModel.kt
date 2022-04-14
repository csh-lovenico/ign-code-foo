package tech.tennoji.igncodefoo.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import tech.tennoji.igncodefoo.network.Api
import tech.tennoji.igncodefoo.network.ArticleResponse

class ArticleViewModel : ViewModel() {


    private var viewModelJob = SupervisorJob()

    private val _articleList = MutableLiveData<ArticleResponse>()
    val articleList: LiveData<ArticleResponse>
        get() = _articleList

    private val _openLink = MutableLiveData<String?>()
    val openLink: LiveData<String?>
        get() = _openLink

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    fun getArticleData(startIndex: Int, count: Int) {
        coroutineScope.launch {
            val articleDeferred = Api.retrofitService.getArticleListAsync(startIndex, count)
            try {
                val articleResponse =
                    articleDeferred.await()
                val contentIdList = ArrayList<String>()
                articleResponse.data.forEach {
                    if (it.metadata.headline.contains("Review")) {
                        it.viewType = ArticleAdapter.REVIEW_ARTICLE
                    } else {
                        it.viewType = ArticleAdapter.NORMAL_ARTICLE
                    }
                    contentIdList.add(it.contentId)
                }
                val contentIds = contentIdList.joinToString(",")
                val commentCountResponse =
                    Api.retrofitService.getCommentCountAsync(contentIds).await()
                for (i in 0 until commentCountResponse.count) {
                    for (j in 0 until commentCountResponse.count) {
                        if (articleResponse.data[i].contentId == commentCountResponse.content[j].id) {
                            articleResponse.data[i].commentCount =
                                commentCountResponse.content[j].count
                        }
                    }
                }
                _articleList.postValue(articleResponse)
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

    fun onError() {
        _error.postValue(true)
    }

    fun onErrorToastComplete() {
        _error.value = false
    }

}