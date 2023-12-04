import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NoteServiceTest {
    val service = NoteService

    @Before
    fun clearBeforeTest() {
        service.clear()
    }

    //    проверка добавления заметки
    @Test
    fun add() {
        service.add(Note(title = "Note1", text = "Текст заметки 1"))
        val nid = service.add(
            Note(title = "Note2", text = "Текст заметки 2")
        )
        assertEquals(2, nid)
    }

    //    проверка успешного удаления заметки
    @Test
    fun deleteTrue() {
        val nid = service.add(Note(title = "Note1", text = "Текст заметки 1"))
        service.add(Note(title = "Note2", text = "Текст заметки 2"))
        val result = service.delete(nid)
        assertTrue(result)
    }

    //    проверка неуспешного удаления заметки
    @Test
    fun deleteFalse() {
        service.add(Note(title = "Note1", text = "Текст заметки 1"))
        val result = service.delete(3)
        assertFalse(result)
    }

    //    проверка успешного обновления замети
    @Test
    fun updateIsTrue() {
        service.add(Note(title = "Note1", text = "Текст заметки 1"))
        service.add(Note(title = "Note2", text = "Текст заметки 2"))

        val updateNote = Note(nid = 2, title = "Note3", text = "Текст заметки 3")
        val result = service.update(updateNote)

        assertTrue( result )

    }

    //    проверка неуспешного обновления замети
    @Test
    fun updateIsFalse() {
        service.add(Note(title = "Note1", text = "Текст заметки 1"))
        service.add(Note(title = "Note2", text = "Текст заметки 2"))

        val updateNote = Note(nid = 3, title = "Note3", text = "Текст заметки 3")
        val result = service.update(updateNote)

        assertFalse(result)

    }

}