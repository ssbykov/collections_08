object NoteService {
    private var notes = mutableListOf<Note>()
    private var id = 0

    private var comments = mutableListOf<Comment>()
    private var guid = 0

    //    метод добавления заметки
    fun addNote(note: Note): Note {
        notes.add(note.copy(id = ++id))
        return notes.last()
    }

    // метод удаления заметки с удалением всех комментариев
    fun deleteNote(id: Int): Boolean {
        return notes.find { it.id == id && !it.isDeleted }?.let { note ->
            notes[notes.indexOf(note)] = note.copy(isDeleted = true)
            comments.filter { it.noteId == id }.forEach {
                deleteComment(it.guid)
            }
            true
        } ?: false
    }

    //    метод изменения заметки
    fun updateNote(note: Note): Boolean {
        return notes.find { it.id == note.id && !it.isDeleted }?.let { findNote ->
            notes[notes.indexOf(findNote)] = note.copy()
            true
        } ?: false
    }

    // метод получения заметки по id
    fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id && !it.isDeleted }
    }

    // метод получения всех заметок
    fun getNotes(): List<Note> {
        return notes.filter { !it.isDeleted }
    }

    // добавление комментария к заметке с заданным id
    fun createComment(noteId: Int, comment: Comment): Comment {
        comments.add(comment.copy(guid = ++guid, noteId = noteId))
        return comments.last()
    }

    // метод удаления комментария
    fun deleteComment(guid: Int): Boolean {
        return comments.find { it.guid == guid && !it.isDeleted }?.let { comment ->
            comments[comments.indexOf(comment)] = comment.copy(isDeleted = true)
            true
        } ?: false
    }

    // метод изменения комментария
    fun updateComment(comment: Comment): Boolean {
        return comments.find { it.guid == comment.guid && !it.isDeleted }?.let { findComment ->
            comments[comments.indexOf(findComment)] = comment.copy()
            true
        } ?: false
    }

    //    метод получения всех комментариев
    fun getComments(noteId: Int): List<Comment> {
        return comments.filter { it.noteId == noteId && !it.isDeleted }
    }

    //  метод восстановления удаленного комментария
    fun restoreComment(guid: Int): Boolean {
        return comments.find {
            val note = getNoteById(it.noteId)
            it.guid == guid && it.isDeleted && note != null && !note.isDeleted
        }?.let { findComment ->
            comments[comments.indexOf(findComment)] = findComment.copy(isDeleted = false)
            true
        } ?: false
    }

    //    метод очистки данных
    fun clear() {
        notes.clear()
        id = 0

        comments.clear()
        guid = 0

    }
}