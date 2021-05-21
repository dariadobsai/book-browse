package hu.dobszai.bookbrowse.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.dobszai.bookbrowse.base.BaseViewModel
import hu.dobszai.bookbrowse.database.BookDatabase.Companion.getDatabaseInstance
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.repo.BookRepository
import kotlinx.coroutines.launch
import timber.log.Timber

open class BookViewModel(app: Application) : BaseViewModel(app) {

    private val database = getDatabaseInstance(app)
    private val bookRepository = BookRepository(database)

    private val _books = MutableLiveData<List<Book>>(mutableListOf())
    val books: LiveData<List<Book>> get() = _books

    private val _totalItems = MutableLiveData<Int>()
    val totalItems: LiveData<Int> get() = _totalItems

    val favoriteBooks: LiveData<List<Book>> = bookRepository.favoriteBooks

    fun findBook(searchInput: String) {
        viewModelScope.launch {
            try {
                val books = bookRepository.fetchBooks(searchInput)
                _books.value = books.items
                _totalItems.value = books.totalItems
            } catch (t: Throwable) {
                Timber.d(t.toString())
            }
        }
    }

    fun favoriteOrUnfavoriteBook(book: Book, isFavorite : Boolean) {
        viewModelScope.launch {
           bookRepository.markBoosAsFavorite(book, isFavorite)
        }
    }
}