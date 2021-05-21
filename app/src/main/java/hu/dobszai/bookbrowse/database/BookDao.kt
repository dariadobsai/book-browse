package hu.dobszai.bookbrowse.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.dobszai.bookbrowse.models.Book

@Dao
interface BookDao {

    /**
     * Get all books
     */
    @Query("SELECT * FROM favorites_table")
    fun getAllBooks(): LiveData<List<Book>>

    /**
     * Delete a book
     */
    @Query("DELETE FROM favorites_table WHERE id = :id")
    fun deleteBook(id: String)

    /**
     * Insert a book
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteBook(vararg book: Book)


    /**
     * Check if book exists
     */
    @Query("SELECT EXISTS(SELECT * FROM favorites_table WHERE id = :id)")
    fun isBookFavorite(id : String) : Boolean
}