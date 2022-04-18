package tech.tennoji.igncodefoo.binding

import android.graphics.Paint
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.databinding.BindingAdapter
import coil.load
import tech.tennoji.igncodefoo.R
import tech.tennoji.igncodefoo.network.VideoData
import java.time.Duration
import java.time.Instant

@BindingAdapter("videoTime")
fun TextView.setVideoTime(item: VideoData?) {
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

@BindingAdapter("videoImage")
fun ImageFilterView.setVideoImage(item: VideoData?) {
    item?.let {
        load(it.thumbnails[1].url)
    }
}

@BindingAdapter("videoHeadline")
fun TextView.setVideoHeadline(item: VideoData?) {
    item?.let {
        text = it.metadata.title
    }
}

@BindingAdapter("videoSeries")
fun TextView.setVideoSeries(item: VideoData?) {
    item?.let {
        if (it.metadata.videoSeries != "none") {
            text = it.metadata.videoSeries
            paintFlags = paintFlags.or(Paint.UNDERLINE_TEXT_FLAG)
        }
    }
}

@BindingAdapter("videoComment")
fun Button.setVideoComment(item: VideoData?) {
    item?.let {
        text = it.commentCount.toString()
    }
}