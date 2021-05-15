package hu.dobszai.bookbrowse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.databinding.ListBooksBinding
import hu.dobszai.bookbrowse.models.Book

class BookListAdapter(val clickListener: ClickListener) :
    RecyclerView.Adapter<BookListAdapter.BooksViewHolder>() {

    private var books: List<Book> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BooksViewHolder {

        val binding: ListBooksBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_books,
            parent,
            false
        )

        return BooksViewHolder(binding)
    }

    interface ClickListener {
        fun onBookClick(book: Book)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun setBookList(books: List<Book>) {
        if (this.books !== books)
            this.books = books

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(books[position])
    }

    inner class BooksViewHolder(val binding: ListBooksBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(book: Book) {
            with(binding) {
                bookModel = book
                executePendingBindings()
            }
        }

        override fun onClick(v: View?) {
            clickListener.onBookClick(books[adapterPosition])
        }
    }
}
