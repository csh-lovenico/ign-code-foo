package tech.tennoji.igncodefoo.binding

import android.graphics.Paint
import android.view.View
import android.widget.Button
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
        val timePosted = Instant.parse(it.metadata.publishDate.replace("+0000", "Z"))
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
            if (it.authors[0].thumbnail != "") {
                load(it.authors[0].thumbnail)
            } else load(R.mipmap.ic_launcher)
        }
    }
}

@BindingAdapter("authorName")
fun TextView.setAuthorName(item: ArticleData?) {
    item?.let {
        if (it.authors.isNotEmpty()) {
            text = it.authors[0].name
        }
    }
}

@BindingAdapter("game")
fun TextView.getGame(item: ArticleData?) {
    item?.let {
        if (it.metadata.objectName != null) {
            text = it.metadata.objectName
            paintFlags = paintFlags.or(Paint.UNDERLINE_TEXT_FLAG)
        } else {
            visibility = View.INVISIBLE
        }
    }
}

@BindingAdapter("commentCount")
fun Button.getCommentCount(item: ArticleData?) {
    item?.let {
        text = it.commentCount.toString()
    }
}