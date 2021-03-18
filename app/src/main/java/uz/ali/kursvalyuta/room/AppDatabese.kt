package uz.ali.kursvalyuta.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.ali.kursvalyuta.ModelServer.DataModelDate
import uz.ali.kursvalyuta.ModelServer.DataModelItem
import uz.ali.kursvalyuta.ModelServer.DataModelItem2


//@Database(entities = [DataModelItem::class], version = 2)
@Database(
    entities = [DataModelItem::class, DataModelItem2::class,DataModelDate::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        var instances: AppDatabase? = null

        fun getInstance() = instances?.getUserDao()

        fun init(context: Context) {
            instances = Room.databaseBuilder(
                context, AppDatabase::class.java,
                "kurs.db"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}
