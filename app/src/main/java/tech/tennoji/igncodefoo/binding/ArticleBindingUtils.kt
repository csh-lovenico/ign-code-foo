package tech.tennoji.igncodefoo.binding

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.databinding.BindingAdapter
import coil.load
import tech.tennoji.igncodefoo.R
import tech.tennoji.igncodefoo.network.ArticleData
import java.time.Duration
import java.time.Instant

@BindingAdapter("time")
fun TextView.setTime(item: ArticleData?) {
    item?.let {
        val timePosted = Instant.parse(it.metadata.publishDate)
        val timeNow = Instant.now()
        val duration = Duration.between(timePosted, timeNow)
        val minutes = duration.toMinutes()
        text = context.resources.getString(R.string.feed_time, minutes)
    }
}

@BindingAdapter("headline")
fun TextView.setHeadline(item: ArticleData?) {
    item?.let {
        text = it.metadata.headline
    }
}

@BindingAdapter("image")
fun ImageFilterView.setImage(item: ArticleData?) {
    item?.let {
        load(it.thumbnails[1].url)
    }
}

@BindingAdapter("description")
fun TextView.setDescription(item: ArticleData?) {
    item?.let {
        text = it.metadata.description
    }
}

@BindingAdapter("authorAvatar")
fun ImageFilterView.getAuthorAvatar(item: ArticleData?) {
    item?.let {
        if (it.authors.isNotEmpty()) {
            load(it.authors[0].thumbnail)
        } else {
            visibility = View.GONE
        }
    }
}

@BindingAdapter("authorName")
fun TextView.setAuthorName(item: ArticleData?){
    item?.let {
        if(it.authors.isNotEmpty()) {
            text = it.authors[0].name
        }
    }
}

@BindingAdapter("game")
fun TextView.getGame(item: ArticleData?) {
    item?.let {
        if (it.metadata.objectName!=null) {
            text = it.metadata.objectName
        }
    }
}

@BindingAdapter("commentCount")
fun TextView.getCommentCount(item: ArticleData?){
    item?.let {
        text = it.commentCount.toString()
    }
}