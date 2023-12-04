data class Note(
    val id: Int = 0,
    val title: String,
    val text: String,
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    val isDelete: Boolean = false


)

data class Comment(
    val guid: Int = 0,
    val noteId: Int = 0,
    val message: String,
    val privacy: Int = 0,
    val isDeleted: Boolean = false

)

class NoteNotFoundException(): RuntimeException("Данной заметки не существует")