package hu.dobszai.bookbrowse.repo

import hu.dobszai.bookbrowse.api.BooksApi
import hu.dobszai.bookbrowse.models.BookResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepository {

    suspend fun fetchBooks(searchInput: String): BookResponse {
        return withContext(Dispatchers.IO) {
            val booksDeffered = BooksApi.retrofitService.getBooksAsync(searchInput)
            val books = booksDeffered.await()
            books
        }
    }
}