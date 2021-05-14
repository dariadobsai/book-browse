package hu.dobszai.bookbrowse.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.base.BaseViewModel
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.repo.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(app: Application) : BaseViewModel(app) {

    private val bookRepository = BookRepository()

    private val _books = MutableLiveData<List<Book>>(mutableListOf())
    val books : LiveData<List<Book>> get() = _books

    private val totalItems = MutableLiveData<Int>()

    fun fetchBooks(searchInput : String) {
        viewModelScope.launch {
            try {
               val bookResponse = bookRepository.fetchBooks(searchInput)

            } catch (t: Throwable) {

            }
        }
    }

    fun validateSearchField(searchInput : String): Boolean {
        if (searchInput.isNullOrEmpty()) {
            showSnackBarInt.value = R.string.book_title_required
            return false
        }

        return false
    }
}