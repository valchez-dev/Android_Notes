package my.personal.notes.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "body") var body: String?,
    @ColumnInfo(name = "color") var color: String?,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "image_path") var imagePath: String?,
) {

    override fun toString(): String {
        return "ID: + $id + , + Title: $title + ; + " +
                "Body: + $body + ; + Color: + $color + Date: $date"
    }
}
