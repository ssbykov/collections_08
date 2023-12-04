object NoteService {
    private var notes = mutableListOf<Note>()
    private var nid = 0

    //    метод добавления заметки
    fun add(note: Note): Int {
        notes.add(note.copy(nid = ++nid))
        return nid
    }

    // метод удаления заметки
    fun delete(nid: Int): Boolean {
        return notes.find { it.nid == nid }?.let { note ->
            notes[notes.indexOf(note)]
            true
        } ?: false
    }

    //    метод изменения заметки
    fun update(note: Note): Boolean {
        for ((index, findNote) in notes.withIndex()) {
            if (findNote.nid == note.nid) {
                notes[index] = note.copy()
                return true
            }
        }
        return false
    }



    //    метод очистки данных
    fun clear() {
        notes.clear()
        nid = 0
    }
}