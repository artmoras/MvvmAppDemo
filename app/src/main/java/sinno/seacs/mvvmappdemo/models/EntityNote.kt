package sinno.seacs.mvvmappdemo.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class EntityNote(
    @PrimaryKey(autoGenerate = true) var id: Long? = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "note") var note: String,
    @ColumnInfo(name = "date") var date: String,
) : java.io.Serializable