package my.personal.notes.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String?,
    @ColumnInfo(name = "color") val color: Int? = -1,
    @ColumnInfo(name = "date") val date: String,
): Parcelable {


    override fun toString(): String {
        return "ID: + $id + , + Title: $title + ; + " +
                "Body: + $body + ; + Color: + $color + Date: $date"
    }
}
