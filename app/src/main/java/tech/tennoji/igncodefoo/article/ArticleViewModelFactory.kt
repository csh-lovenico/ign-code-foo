package tech.tennoji.igncodefoo.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArticleViewModelFactory(
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}