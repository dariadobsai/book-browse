package hu.dobszai.bookbrowse.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import hu.dobszai.bookbrowse.api.BooksApi
import hu.dobszai.bookbrowse.data.BookDatabase
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.models.BookResponse
import hu.dobszai.bookbrowse.models.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepository(private val database: BookDatabase) {

    val favoriteBooks: LiveData<List<Book>> =
        Transformations.map(database.bookDao.getAllBooks()) { list ->
            list.asDomainModel()
        }

    suspend fun fetchBooks(searchInput: String): BookResponse {
        return withContext(Dispatchers.IO) {
            val booksDeffered = BooksApi.retrofitService.getBooksAsync(searchInput)
            val books = booksDeffered.await()
            books
        }
    }

    suspend fun markBoosAsFavorite(book: Book): Boolean {
        var isFavorite: Boolean

        withContext(Dispatchers.IO) {

            database.bookDao.also {
                isFavorite = it.isBookFavorite(book.id)
                if (isFavorite) {
                    it.deleteBook(book.id)

                } else {
                    it.insertFavoriteBook(book)

                }
            }

        }

        return isFavorite
    }
}