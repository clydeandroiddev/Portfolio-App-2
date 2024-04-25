package com.jczm.dataloader.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jczm.dataloader.data.database.entities.AuctionEntity
import com.jczm.dataloader.data.database.entities.CarEntity
import com.jczm.dataloader.data.model.Car
import com.jczm.dataloader.data.model.CarData
import com.jczm.dataloader.data.model.SortType
import com.jczm.dataloader.data.repository.CarRepository
import com.jczm.dataloader.util.base.BaseViewModel
import com.jczm.dataloader.util.helper.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: CarRepository) : BaseViewModel() {


    private val _proceed = MutableLiveData<Event<Boolean>>()
    val proceed : LiveData<Event<Boolean>> = _proceed

    private val _showError = MutableLiveData<Event<String>>()
    val showError : LiveData<Event<String>> = _showError

    private val _showCarLists = MutableLiveData<Event<List<CarData>>>()
    val showCarLists : LiveData<Event<List<CarData>>> = _showCarLists


    fun hasCarData() = repository.haveCarsData()
    fun storeCarsData(carJson: String) {
        try{
            val carJsonObject = JSONObject(carJson)
            val errorObject = carJsonObject.getJSONObject("Error")
            val carObject = carJsonObject.getJSONArray("Cars").toString()
            if(errorObject.length() == 0 && carObject.isNotEmpty()){
                parseJsonToCars(carObject){ cars , error ->
                    if(cars.isNotEmpty()){
                        val carEntityList = arrayListOf<CarEntity>()
                        val auctionEntityList= arrayListOf<AuctionEntity>()
                        cars.forEach { data ->
                            carEntityList.add(
                                CarEntity(
                                    data.carID,
                                    data.image,
                                    data.titleEn,
                                    data.titleAr
                                )
                            )
                            auctionEntityList.add(
                                AuctionEntity(
                                    0,
                                    data.carID,
                                    data.auctionInfo.bids,
                                    data.auctionInfo.endTimeInSec,
                                    data.auctionInfo.currencyEn,
                                    data.auctionInfo.currencyAr,
                                    data.auctionInfo.currentPrice,
                                    data.auctionInfo.priority
                                )
                            )
                        }
                        invokeAtBackground {
                            repository.insertCarsData(carEntityList, auctionEntityList)
                        }
                        _proceed.value = Event(true)
                    }else {
                        _showError.value = Event(error)
                    }
                }
            }else {
                _showError.value = Event("There is an error in your data.")
            }
        }catch (j : JSONException){
            _showError.value = Event("There is an error in parsing your data.")
        }
        catch (n : NullPointerException){
            _showError.value = Event("There is a null value in your data.")
        }catch (e : Exception){
            e.printStackTrace()
            _showError.value = Event("There is an error in your data.")
        }



    }

    private fun parseJsonToCars(jsonString : String, result: (List<Car>, String) -> Unit) {
        try {
            val gson = Gson()
            result(gson.fromJson(jsonString, object : TypeToken<List<Car>>() {}.type), "")
        } catch (e : JSONException){
            result(arrayListOf(), "There is an error parsing your data.")
        }
    }

    fun fetchCarsData(){
        invoke {
            val carList = repository.fetchCarList()
            if(carList.isNotEmpty()){
                _showCarLists.value = Event(carList)
            }else {
                _showError.value = Event("Unable to retrieve data from database.")
            }
        }
    }

    fun updateLanguage(name: String) {
        repository.updateLanguage(name)
    }

    fun fetchCarsDataWith(sortType: SortType) {
        invoke {
            val carList = repository.fetchCarListBy(sortType)
            if(carList.isNotEmpty()){
                _showCarLists.value = Event(carList)
            }else {
                _showError.value = Event("Unable to retrieve data from database.")
            }
        }
    }

    fun searchCarsDataWith(query: String, currSortType: SortType) {
        invoke {
            val carList = repository.searchCarsDataWith(query, currSortType)
            _showCarLists.value = Event(carList)
        }
    }
}