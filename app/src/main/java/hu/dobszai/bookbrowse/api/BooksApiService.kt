package hu.dobszai.bookbrowse.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.dobszai.bookbrowse.api.BookHttpClient.Companion.getClient
import hu.dobszai.bookbrowse.models.BookResponse
import hu.dobszai.bookbrowse.utils.QUERY_PARAMETER_KEY
import hu.dobszai.bookbrowse.utils.VOLUMES_PARAMETER_KEY
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  Documentation for Books API can be found at
 *  https://developers.google.com/books/docs/v1/reference/volumes/list
 */
private const val BASE_URL = "https://www.googleapis.com/books/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(getClient())
    .baseUrl(BASE_URL)
    .build()

interface BooksApiService {

    @GET(VOLUMES_PARAMETER_KEY)
    fun getBooksAsync(@Query(QUERY_PARAMETER_KEY) searchInput: String): Deferred<BookResponse>
}

object BooksApi {
    val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
}