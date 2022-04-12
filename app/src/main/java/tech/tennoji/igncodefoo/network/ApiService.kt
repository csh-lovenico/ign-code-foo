package tech.tennoji.igncodefoo.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://ign-apis.herokuapp.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("videos")
    fun getVideoListAsync(
        @Query("startIndex") startIndex: Int,
        @Query("count") count: Int
    ): Deferred<VideoResponse>

    @GET("articles")
    fun getArticleListAsync(
        @Query("startIndex") startIndex: Int,
        @Query("count") count: Int
    ): Deferred<ArticleResponse>

    @GET("comments")
    fun getCommentCountAsync(
        @Query("ids") ids: String
    ): Deferred<CommentCountResponse>
}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}