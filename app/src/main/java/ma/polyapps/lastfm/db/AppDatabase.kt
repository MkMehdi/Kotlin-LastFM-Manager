package ma.polyapps.lastfm.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import ma.polyapps.lastfm.db.converter.ArtistConverters
import ma.polyapps.lastfm.db.converter.TracksConverters
import ma.polyapps.lastfm.db.dao.AlbumDao
import ma.polyapps.lastfm.db.entity.AlbumEntity
import ma.polyapps.lastfm.db.entity.ArtistEntity
import ma.polyapps.lastfm.db.entity.TrackEntity


/**
 * Created by Elmehdi Mellouk on 21/09/18.
 * mellouk.elmehdi@gmail.com
 */

@Database(entities = [(AlbumEntity::class), (TrackEntity::class), (ArtistEntity::class)], version = 1,exportSchema = false)
@TypeConverters(ArtistConverters::class,TracksConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "lastFm.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}
