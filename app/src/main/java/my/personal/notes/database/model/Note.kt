package my.personal.notes.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(

    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String?,
    @ColumnInfo(name = "color") val color: String?,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "image_path") val imagePath: String?
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

    override fun toString(): String {
        return "ID: + $id + , + Title: $title + ; + " +
                "Body: + $body + ; + Color: + $color + Date: $date"
    }
}
