package sinno.seacs.mvvmappdemo.database

import androidx.lifecycle.LiveData
import sinno.seacs.mvvmappdemo.models.EntityNote

class RepoNote(private val daoNote: DaoNote) {

    val allNotes: LiveData<List<EntityNote>> = daoNote.getAllNotes()
    suspend fun insert(note: EntityNote) = daoNote.insert(note)
    suspend fun delete(note: EntityNote) = daoNote.delete(note)
    suspend fun update(note: EntityNote) = daoNote.update(note.id?.toInt(), note.title, note.note)
}