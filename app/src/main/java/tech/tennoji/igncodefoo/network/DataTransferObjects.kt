package tech.tennoji.igncodefoo.network

import com.squareup.moshi.JsonClass

data class Thumbnail(
    val url: String,
    val size: String,
    val width: Int,
    val height: Int
)

data class ArticleMetadata(
    val headline: String,
    val description: String?,
    val publishDate: String,
    val slug: String,
    val networks: List<String>,
    val state: String,
    val objectName: String?
)

data class ArticleData(
    val contentId: String,
    val contentType: String,
    val thumbnails: List<Thumbnail>,
    val metadata: ArticleMetadata,
    val tags: List<String>,
    val authors: List<Author>,
    // these fields are not in the json, but we can use them to store extra data
    var commentCount: Int?,
    var viewType:Int?
)

data class Author(
    val name: String,
    val thumbnail: String
)

@JsonClass(generateAdapter = true)
data class ArticleResponse(
    val count: Int,
    val startIndex: Int,
    val data: List<ArticleData>
)

data class VideoMetadata(
    val title: String,
    val description: String,
    val publishDate: String,
    val slug: String,
    val networks: List<String>,
    val state: String,
    val duration: Int,
    val videoSeries: String
)

data class VideoAsset(
    val url: String,
    val height: Int,
    val width: Int
)

data class VideoData(
    val contentId: String,
    val contentType: String,
    val thumbnails: List<Thumbnail>,
    val metadata: VideoMetadata,
    val tags: List<String>,
    val assets: List<VideoAsset>,
    var commentCount: Int?
)

@JsonClass(generateAdapter = true)
data class VideoResponse(
    val count: Int,
    val startIndex: Int,
    val data: List<VideoData>
)

data class CommentCount(
    val id: String,
    val count: Int
)

@JsonClass(generateAdapter = true)
data class CommentCountResponse(
    val count: Int,
    val content: List<CommentCount>
)