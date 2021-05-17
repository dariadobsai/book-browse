package hu.dobszai.bookbrowse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.dobszai.bookbrowse.databinding.ItemBookBinding
import hu.dobszai.bookbrowse.models.Book

class DetailListAdapter : RecyclerView.Adapter<DetailListAdapter.DetailViewHolder>() {

    private var list: List<Book> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailViewHolder {

        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size

    }

    fun setBookList(books: List<Book>) {
        if (this.list !== books)
            this.list = books

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class DetailViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            with(binding) {
                bookModel = book
                executePendingBindings()
            }
        }
    }
}