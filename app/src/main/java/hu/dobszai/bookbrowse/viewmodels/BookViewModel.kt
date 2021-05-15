package hu.dobszai.bookbrowse.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.dobszai.bookbrowse.base.BaseViewModel
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.repo.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(app: Application) : BaseViewModel(app) {

    private val bookRepository = BookRepository()

    private val _books = MutableLiveData<List<Book>>(mutableListOf())
    val books : LiveData<List<Book>> get() = _books

    private val totalItems = MutableLiveData<Int>()

    fun findBook(searchInput : String) {
        viewModelScope.launch {
            try {
                _books.value = bookRepository.fetchBooks(searchInput).items

            } catch (t: Throwable) {

            }
        }
    }
}