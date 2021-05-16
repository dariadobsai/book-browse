package hu.dobszai.bookbrowse.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import hu.dobszai.bookbrowse.databinding.ListBooksBinding
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.viewmodels.BookViewModel

class BookListFavAdapter(val clickListener: ClickListener,
                         lifecycleOwner: LifecycleOwner,
                         val viewModel: BookViewModel,
) :
    RecyclerView.Adapter<BookListFavAdapter.BooksViewHolder>() {

    private var list: List<Book> = mutableListOf()

    init {
        viewModel.isFavorite.observe(lifecycleOwner) {
            Log.d("FAVORITE", it.toString())
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BooksViewHolder {

        val binding = dataBinding(parent)

        return BooksViewHolder(binding)
    }


    private fun dataBinding(parent: ViewGroup): ListBooksBinding {
        return ListBooksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }


    interface ClickListener {
        fun onBookClick(book: Book)
    }

    override fun getItemCount(): Int {
        return if (list != null) list.size else 0

    }

    fun setBookList(books: List<Book>) {
        if (this.list !== books)
            this.list = books

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class BooksViewHolder(private val binding: ListBooksBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(book: Book) {
            with(binding) {
                bookViewModel = viewModel
                bookModel = book
                executePendingBindings()
                btnAddFavorite.setOnClickListener {
                    viewModel.favoriteOrUnfavoriteBook(book)
                }
            }
        }


        override fun onClick(v: View?) {
            clickListener.onBookClick(list[adapterPosition])
        }
    }
}
