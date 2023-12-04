data class Note(
    val nid: Int = 0,
    val title: String,
    val text: String,
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,


)


class NoteNotFoundException(): RuntimeException("Данной заметки не существует")