package ma.polyapps.lastfm.db.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ma.polyapps.lastfm.db.entity.TrackEntity
import java.util.*


/**
 * Created by Elmehdi Mellouk on 23/09/18.
 * ${COMPANY}
 * mellouk.elmehdi@gmail.com
 */
class TracksConverters {

    var gson = Gson()

    @TypeConverter
    fun stringToTracks(data: String?): List<TrackEntity> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<TrackEntity>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun TracksToString(someObjects: List<TrackEntity>): String {
        return gson.toJson(someObjects)
    }
}