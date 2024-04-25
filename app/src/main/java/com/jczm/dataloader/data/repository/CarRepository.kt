package com.jczm.dataloader.data.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.jczm.dataloader.data.database.CarDatabase
import com.jczm.dataloader.data.database.entities.AuctionEntity
import com.jczm.dataloader.data.database.entities.CarDataEntity
import com.jczm.dataloader.data.database.entities.CarDataEntity.Companion.toDomainList
import com.jczm.dataloader.data.database.entities.CarEntity
import com.jczm.dataloader.data.model.CarData
import com.jczm.dataloader.data.model.SortType
import com.jczm.dataloader.util.extensions.isArabic
import com.jczm.dataloader.util.helper.LocaleUtilHelper
import com.jczm.dataloader.util.helper.LocaleUtilHelper.Companion.get
import com.jczm.dataloader.util.helper.LocaleUtilHelper.Companion.set
import org.intellij.lang.annotations.Language
import javax.inject.Inject

interface CarRepository {
    suspend fun insertCarsData(carList : List<CarEntity>, auctionInfoList : List<AuctionEntity>)

    suspend fun fetchCarList() : List<CarData>
    fun haveCarsData() : Boolean
    fun updateLanguage(name: String)
    suspend fun fetchCarListBy(sortType: SortType): List<CarData>
    suspend fun searchCarsDataWith(query: String, currSortType: SortType): List<CarData>
}

class CarRepositoryImpl @Inject constructor(
    private val carDatabase : CarDatabase,
    private val localeUtilHelper: LocaleUtilHelper
) : CarRepository {
    override suspend fun insertCarsData(
        carList: List<CarEntity>,
        auctionInfoList: List<AuctionEntity>
    ) {
        carDatabase.carDao().insertAllCars(carList)
        carDatabase.auctionInfoDao().insertAllAuctionInfo(auctionInfoList)
    }

    override suspend fun fetchCarList(): List<CarData> {
        val language = localeUtilHelper.pref[LocaleUtilHelper.LANG, "en"] ?: "en"
        val listOfCarDataEntity = carDatabase.carDao().getCarsList()
        return listOfCarDataEntity.toDomainList(language)
    }

    override fun haveCarsData(): Boolean = carDatabase.carDao().getCount() > 0
    override fun updateLanguage(name: String) {
        localeUtilHelper.pref[LocaleUtilHelper.LANG] = name
    }

    override suspend fun fetchCarListBy(sortType: SortType): List<CarData> {
        val language = localeUtilHelper.pref[LocaleUtilHelper.LANG, "en"] ?: "en"
        val listOfCarDAtaEntity = carDatabase.carDao().getCartListWith(generateQueryBy<CarDataEntity>(sortType))
        return listOfCarDAtaEntity.toDomainList(language)
    }

    override suspend fun searchCarsDataWith(query: String, currSortType: SortType): List<CarData> {
        val language = localeUtilHelper.pref[LocaleUtilHelper.LANG, "en"] ?: "en"
        val listOfCarDAtaEntity = carDatabase.carDao().getCartListWith(generateSearchQuery<CarDataEntity>(query,currSortType))
        return listOfCarDAtaEntity.toDomainList(language)
    }

    private inline fun <reified T: Any>generateSearchQuery(query: String, sortType: SortType) : SimpleSQLiteQuery{
        val queryStrBuilder = StringBuilder()
        queryStrBuilder.append("SELECT cars.*, auctionInfo.* FROM cars ")
        queryStrBuilder.append("INNER JOIN auctionInfo ON cars.id = auctionInfo.carId ")
        if(query.isArabic()){
            queryStrBuilder.append("WHERE cars.titleAR LIKE '%${query}%' ")
        }else{
            queryStrBuilder.append("WHERE cars.titleEN LIKE '%${query}%' ")
        }
        queryStrBuilder.append("ORDER BY ")
        when(sortType){
            SortType.PRIORTY_ASC ->  queryStrBuilder.append("auctionInfo.priority ASC")
            SortType.PRIORITY_DESC -> queryStrBuilder.append("auctionInfo.priority DESC")
            SortType.PRICE_ASC ->  queryStrBuilder.append("auctionInfo.currentPrice DESC")
            SortType.PRICE_DESC -> queryStrBuilder.append("auctionInfo.currentPrice ASC")
            SortType.BIDS -> queryStrBuilder.append("auctionInfo.bids DESC")
        }
        return SimpleSQLiteQuery(queryStrBuilder.toString(), arrayOf<T>())
    }

    private inline fun <reified T: Any>generateQueryBy(sortType: SortType) : SimpleSQLiteQuery{
        val queryStrBuilder = StringBuilder()
        queryStrBuilder.append("SELECT cars.*, auctionInfo.* FROM cars ")
        queryStrBuilder.append("INNER JOIN auctionInfo ON cars.id = auctionInfo.carId ORDER BY ")
        when(sortType){
            SortType.PRIORTY_ASC ->  queryStrBuilder.append("auctionInfo.priority ASC")
            SortType.PRIORITY_DESC -> queryStrBuilder.append("auctionInfo.priority DESC")
            SortType.PRICE_ASC ->  queryStrBuilder.append("auctionInfo.currentPrice DESC")
            SortType.PRICE_DESC -> queryStrBuilder.append("auctionInfo.currentPrice ASC")
            SortType.BIDS -> queryStrBuilder.append("auctionInfo.bids DESC")
        }
       return SimpleSQLiteQuery(queryStrBuilder.toString(), arrayOf<T>())
    }


}