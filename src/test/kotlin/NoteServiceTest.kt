import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NoteServiceTest {
    private val service = NoteService

    @Before
    fun clearBeforeTest() {
        service.clear()
    }

    //    проверка добавления заметки
    @Test
    fun addNote() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val note = service.addNote(
            Note(title = "Note2", text = "Текст заметки 2")
        )
        assertEquals(2, note.id)
    }

    //    проверка успешного удаления заметки
    @Test
    fun deleteNoteTrue() {
        val note = service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        service.addNote(Note(title = "Note2", text = "Текст заметки 2"))
        service.createComment(1, Comment(message = "Комментарий к заметке Note 1"))

        val result = service.deleteNote(note.id)

        assertTrue(result)
        assertTrue(service.getComments(noteId = note.id).isEmpty())
    }

    //    проверка неуспешного удаления несуществующей заметки
    @Test
    fun deleteNoteFalse() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val result = service.deleteNote(3)
        assertFalse(result)
    }

    //    проверка неуспешного удаления несуществующей заметки
    @Test
    fun deleteNoteIsDeleted() {
        val note = service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))

        service.deleteNote(note.id)

        val result = service.deleteNote(note.id)
        assertFalse(result)
    }

    //    проверка успешного обновления замети
    @Test
    fun updateNoteIsTrue() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        service.addNote(Note(title = "Note2", text = "Текст заметки 2"))

        val updateNote = Note(id = 2, title = "Note3", text = "Текст заметки 3")
        val result = service.updateNote(updateNote)

        assertTrue( result )

    }

    //    проверка неуспешного обновления несуществующей замети
    @Test
    fun updateNoteIsFalse() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        service.addNote(Note(title = "Note2", text = "Текст заметки 2"))

        val updateNote = Note(id = 3, title = "Note3", text = "Текст заметки 3")
        val result = service.updateNote(updateNote)

        assertFalse(result)

    }

    //    проверка неуспешного обновления удаленной замети
    @Test
    fun updateNoteIsDeleted() {
        val note = service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        service.addNote(Note(title = "Note2", text = "Текст заметки 2"))

        val updateNote = Note(note.id, title = "Note3", text = "Текст заметки 3")
        service.deleteNote(note.id)
        val result = service.updateNote(updateNote)

        assertFalse(result)

    }

    //    проверка успешного получения замети по id
    @Test
    fun getNoteByIdIsTrue() {
        val note1 = service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        service.addNote(Note(title = "Note2", text = "Текст заметки 2"))

        val result = service.getNoteById(1)

        assertEquals( result, note1 )

    }

    //    проверка неуспешного получения несуществующей замети по id
    @Test
    fun getNoteByIdIsFalse() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val note = service.addNote(Note(title = "Note2", text = "Текст заметки 2"))

        val result = service.getNoteById(note.id + 1)

        assertNull(result)

    }

    //    проверка неуспешного получения удаленной замети по id
    @Test
    fun getNoteByIdIsDeleted() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val note = service.addNote(Note(title = "Note2", text = "Текст заметки 2"))

        service.deleteNote(note.id)

        val result = service.getNoteById(note.id)

        assertNull(result)

    }

    @Test
    fun getNotes() {
        val note = service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        service.addNote(Note(title = "Note2", text = "Текст заметки 2"))

        service.deleteNote(note.id)
        val result = service.getNotes().size

        assertEquals(1, result)
    }

    //  тест добавления комментария
    @Test
    fun createComment() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val comment = service.createComment(1, Comment(message = "Комментарий к заметке Note 1"))

        assertEquals(Pair(1, 1), Pair(comment.guid, comment.noteId))
    }

    // тест успешного удаления комментария
    @Test
    fun deleteCommentTrue() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val comment = service.createComment(1, Comment(message = "Комментарий к заметке Note 1"))

        val result = service.deleteComment(comment.guid)

        assertTrue(result)
    }

    // тест удаления несуществующего комментария
    @Test
    fun deleteCommentFalse() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val comment = service.createComment(1, Comment(message = "Комментарий к заметке Note 1"))

        val result = service.deleteComment(comment.guid + 1)

        assertFalse(result)
    }

    // тест удаления уже удаленного комментария
    @Test
    fun deleteCommentIsDeleted() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val comment = service.createComment(1, Comment(message = "Комментарий к заметке Note 1"))
        service.deleteComment(comment.guid)

        val result = service.deleteComment(comment.guid)

        assertFalse(result)
    }

    // тест успешного изменения комментария
    @Test
    fun updateCommentTrue() {
        val note = service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val comment = service.createComment(1, Comment(message = "Комментарий к заметке Note 1"))

        val result = service.updateComment(
            Comment(guid = comment.guid, noteId = note.id, "Измененный комментарий")
        )

        assertTrue(result)
    }

    // тест неуспешного изменения комментария
    @Test
    fun updateCommentFalse() {
        val note = service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val comment = service.createComment(note.id, Comment(message = "Комментарий к заметке Note 1"))

        val result = service.updateComment(
            Comment(guid = comment.guid + 1, noteId = note.id, "Измененный комментарий")
        )

        assertFalse(result)
    }

    // тест неуспешного изменения удаленного комментария
    @Test
    fun updateCommentIsDeleted() {
        service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val comment = service.createComment(1, Comment(message = "Комментарий к заметке Note 1"))
        service.deleteComment(comment.guid)

        val result = service.updateComment(Comment(guid = 1, noteId = 1, "Измененный комментарий"))

        assertFalse(result)
    }

    @Test
    fun getComments() {
        val note = service.addNote(Note(title = "Note1", text = "Текст заметки 1"))
        val comment = service.createComment(note.id, Comment(message = "Комментарий к заметке Note 1"))
        service.createComment(note.id, Comment(message = "Еще комментарий к заметке Note 1"))
        service.deleteComment(comment.guid)

        val result = service.getComments(note.id).size

        assertEquals(1, result)
    }
}