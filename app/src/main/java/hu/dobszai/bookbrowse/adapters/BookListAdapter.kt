package hu.dobszai.bookbrowse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.dobszai.bookbrowse.databinding.ListBooksBinding
import hu.dobszai.bookbrowse.models.Book

class BookListAdapter(val clickListener: ClickListener) :
    RecyclerView.Adapter<BookListAdapter.BooksViewHolder>() {

    private var list: List<Book> = mutableListOf()

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

        fun bind(b: Book) {
            with(binding) {
                book = b
                executePendingBindings()
            }
        }

        override fun onClick(v: View?) {
            clickListener.onBookClick(list[adapterPosition])
        }
    }
}
