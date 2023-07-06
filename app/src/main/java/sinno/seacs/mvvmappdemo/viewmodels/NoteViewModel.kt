package sinno.seacs.mvvmappdemo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sinno.seacs.mvvmappdemo.database.Db
import sinno.seacs.mvvmappdemo.database.RepoNote
import sinno.seacs.mvvmappdemo.models.EntityNote

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repoNote: RepoNote
    val allNotes : LiveData<List<EntityNote>>

    init {
        val dao = Db.getDatabase(application).getNoteDao()
        repoNote = RepoNote(dao)
        allNotes = repoNote.allNotes
    }

    fun deleteNote(note: EntityNote) = viewModelScope.launch(Dispatchers.IO) {
        repoNote.delete(note)
    }

    fun insertNote(note: EntityNote) = viewModelScope.launch(Dispatchers.IO) {
        repoNote.insert(note)
    }

    fun updateNote(note: EntityNote) = viewModelScope.launch(Dispatchers.IO) {
        repoNote.update(note)
    }
}