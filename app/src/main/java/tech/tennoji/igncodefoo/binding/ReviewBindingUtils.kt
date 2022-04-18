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


@BindingAdapter("reviewTime")
fun TextView.setReviewTime(item: ArticleData?) {
    item?.let {
        val timePosted = Instant.parse(it.metadata.publishDate.replace("+0000", "Z"))
        val timeNow = Instant.now()
        val duration = Duration.between(timePosted, timeNow)
        text = if (duration.toDays() != 0L) {
            context.resources.getString(R.string.feed_day, duration.toDays())
        } else if (duration.toHours() != 0L) {
            context.resources.getString(R.string.feed_hour, duration.toHours())
        } else {
            val minutes = duration.toMinutes()
            context.resources.getString(R.string.feed_time, minutes)
        }
    }
}

@BindingAdapter("reviewImage")
fun ImageFilterView.setReviewImage(item: ArticleData?) {
    item?.let {
        load(it.thumbnails[1].url)
    }
}

@BindingAdapter("reviewHeadline")
fun TextView.setReviewHeadline(item: ArticleData?) {
    item?.let {
        text = it.metadata.headline
    }
}

@BindingAdapter("reviewAvatar")
fun ImageFilterView.getReviewAvatar(item: ArticleData?) {
    item?.let {
        if (it.authors.isNotEmpty()) {
            if (it.authors[0].thumbnail != "") {
                load(it.authors[0].thumbnail)
            } else load(R.mipmap.ic_launcher)
        }
    }
}

@BindingAdapter("reviewAuthor")
fun TextView.setReviewAuthor(item: ArticleData?) {
    item?.let {
        if (it.authors.isNotEmpty()) {
            text = it.authors[0].name
            paintFlags = paintFlags.or(Paint.UNDERLINE_TEXT_FLAG)
        }
    }
}

@BindingAdapter("reviewGame")
fun TextView.setReviewGame(item: ArticleData?) {
    item?.let {
        if (it.metadata.objectName != null) {
            text = it.metadata.objectName
            paintFlags = paintFlags.or(Paint.UNDERLINE_TEXT_FLAG)
        }
    }
}

@BindingAdapter("reviewComment")
fun Button.setReviewComment(item: ArticleData?) {
    item?.let {
        text = it.commentCount.toString()
    }
}