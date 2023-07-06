package sinno.seacs.mvvmappdemo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import sinno.seacs.mvvmappdemo.models.EntityNote

@Dao
interface DaoNote {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: EntityNote): EntityNote {
        note.id = _insert(note)
        return note
    }

    @Delete
    suspend fun delete(note: EntityNote)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllNotes() : LiveData<List<EntityNote>>

    @Query("UPDATE notes_table SET title = :title, note = :note WHERE id = :id")
    suspend fun update(id: Int?, title: String?, note: String?)

    @Insert
    abstract fun _insert(note: EntityNote): Long
}