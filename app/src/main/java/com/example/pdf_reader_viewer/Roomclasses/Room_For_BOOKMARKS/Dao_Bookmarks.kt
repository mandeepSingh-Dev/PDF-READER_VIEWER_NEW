package com.example.pdf_reader_viewer.Roomclasses.Room_For_BOOKMARKS

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface Dao_Bookmarks {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itemsBookmarks: Items_Bookmarks)

    @Delete
    fun delete(itemsBookmarks: Items_Bookmarks)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(itemsBookmarks: Items_Bookmarks)

    @Query("SELECT * FROM ITEMS_BOOKMARKS")
    fun query(): LiveData<List<Items_Bookmarks>>

    @Query("SELECT * FROM Items_Bookmarks WHERE item_id Like :id")
    fun queryByID(id:Int):LiveData<Items_Bookmarks>

    @Query("SELECT * FROM ITEMS_BOOKMARKS WHERE pdfUri Like :pdfUri")
    fun query(pdfUri: String):Items_Bookmarks

}