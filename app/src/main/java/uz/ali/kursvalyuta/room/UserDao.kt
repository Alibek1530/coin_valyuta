package uz.ali.kursvalyuta.room

import androidx.room.*
import uz.ali.kursvalyuta.ModelServer.*


@Dao
interface UserDao {

    @Query("select * from DataModelItem")
    fun getDayAllDavlat(): List<DataModelItem>

    @Query("select DataModelItem.CcyNm_UZ from DataModelItem")
    fun getAllDavlatUz(): List<String>

    @Query("select DataModelItem.CcyNm_UZC from DataModelItem")
    fun getAllDavlatUzRu(): List<String>

    @Query("select DataModelItem.CcyNm_RU from DataModelItem")
    fun getAllDavlatRu(): List<String>

    @Query("select DataModelItem.CcyNm_EN from DataModelItem")
    fun getAllDavlatEn(): List<String>


    @Query("select DataModelDate.date from DataModelDate GROUP BY DataModelDate.date")
    fun getAllYil(): List<String>


    @Query("select * from DataModelItem2 where DataModelItem2.Ccy1=:rub")
    fun getDavlatAllDay(rub: String): List<DataModelItem2>

    @Query("select * from DataModelItem2 where DataModelItem2.Ccy1=:rub AND DataModelItem2.Date1 LIKE :date ORDER BY DataModelItem2.`key` ASC")//ORDER BY DataModelItem2.Date1 ASC
    fun geGraphDavlatAllDay(rub: String,date: String): List<DataModelItem2>//date .01.2019


    @Query("select * from DataModelItem where DataModelItem.Ccy LIKE :search OR DataModelItem.CcyNm_EN LIKE :search OR DataModelItem.CcyNm_UZ LIKE :search OR DataModelItem.CcyNm_UZC LIKE :search OR DataModelItem.CcyNm_RU LIKE :search")
    fun getSearchDayAllDavlat(search: String): List<DataModelItem>


    @Query("select DataModelItem2.Nominal1 from DataModelItem2 where DataModelItem2.Code1=:code AND DataModelItem2.Date1=:date")
    fun getBool(code: String,date: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: DataModel?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert2(user: DataModelItem2)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDate(date: DataModelDate)

    @Delete
    fun delete(user: DataModelItem)

    @Update
    fun update(user: DataModelItem2)


}