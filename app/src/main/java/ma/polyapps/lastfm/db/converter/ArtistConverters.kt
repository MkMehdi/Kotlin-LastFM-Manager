package ma.polyapps.lastfm.db.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import ma.polyapps.lastfm.db.entity.ArtistEntity


/**
 * Created by Elmehdi Mellouk on 23/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class ArtistConverters {

    var gson = Gson()

    @TypeConverter
    fun stringToTracks(data: String?): ArtistEntity {
        return gson.fromJson(data, ArtistEntity::class.java)
    }

    @TypeConverter
    fun TracksToString(someObjects: ArtistEntity): String {
        return gson.toJson(someObjects)
    }
}